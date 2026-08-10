package com.example.demo.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CartDAO;
import com.example.demo.dao.CheckoutMemberDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.dao.ShoppingOrderDAO;
import com.example.demo.dao.ShoppingOrderItemDAO;
import com.example.demo.model.CartItem;
import com.example.demo.model.CheckoutForm;
import com.example.demo.model.Member;
import com.example.demo.model.OrderCompletionResult;
import com.example.demo.model.ShoppingOrder;
import com.example.demo.model.ShoppingOrderItem;

import lombok.RequiredArgsConstructor;

/**
 * 注文者情報入力・購入内容確認・注文確定を担当します。
 */
@Service
@RequiredArgsConstructor
public class CheckoutService {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    CheckoutService.class);

    private static final Set<String>
            DELIVERY_PAYMENT_METHODS =
                    Set.of(
                            "credit",
                            "bank",
                            "cash_on_delivery",
                            "convenience_store");

    private static final Set<String>
            PICKUP_PAYMENT_METHODS =
                    Set.of(
                            "credit",
                            "bank",
                            "convenience_store",
                            "pay_at_store");

    private static final Set<String>
            VALID_FULFILLMENT_METHODS =
                    Set.of("DELIVERY", "PICKUP");

    private final CheckoutMemberDAO
            checkoutMemberDAO;

    private final CartService
            cartService;

    private final CartDAO
            cartDAO;

    private final ProductDAO
            productDAO;

    private final ShoppingOrderDAO
            shoppingOrderDAO;

    private final ShoppingOrderItemDAO
            shoppingOrderItemDAO;

    private final OrderMailService
            orderMailService;

    public CheckoutForm createInitialForm(
            String memberId) {

        validateMemberId(memberId);

        Member member =
                checkoutMemberDAO
                        .findById(memberId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "会員情報が見つかりません。"));

        CheckoutForm form =
                new CheckoutForm();

        form.setCustomerName(
                emptyIfNull(
                        member.getMemberName()));

        form.setPostalCode(
                emptyIfNull(
                        member.getPostalCode()));

        form.setAddress(
                emptyIfNull(
                        member.getAddress()));

        form.setPhone(
                emptyIfNull(
                        member.getPhoneNumber()));

        form.setEmail(
                emptyIfNull(
                        member.getEmail()));

        form.setPaymentMethod(
                emptyIfNull(
                        member.getPaymentMethod()));

        return form;
    }

    public List<CartItem>
            getCartItemsForCheckout(
                    String memberId) {

        validateMemberId(memberId);

        List<CartItem> cartItems =
                cartService.findCartItems(
                        memberId);

        validateCartItems(
                cartItems,
                getCartFulfillmentMethod(memberId));

        return cartItems;
    }

    public String getCartFulfillmentMethod(
            String memberId) {

        validateMemberId(memberId);

        String fulfillmentMethod =
                cartService.getFulfillmentMethod(memberId);

        if (!VALID_FULFILLMENT_METHODS.contains(
                fulfillmentMethod)) {

            throw new IllegalStateException(
                    "カートの受取方法が設定されていません。");
        }

        return fulfillmentMethod;
    }

    public void validateAndNormalize(
            CheckoutForm form) {

        if (form == null) {
            throw new IllegalArgumentException(
                    "注文者情報が指定されていません。");
        }

        form.setCustomerName(
                trim(form.getCustomerName()));

        form.setPostalCode(
                trim(form.getPostalCode()));

        form.setAddress(
                trim(form.getAddress()));

        form.setPhone(
                trim(form.getPhone()));

        form.setEmail(
                trim(form.getEmail()));

        form.setPaymentMethod(
                trim(form.getPaymentMethod()));

        form.setFulfillmentMethod(
                trim(form.getFulfillmentMethod()));

        form.setPickupDate(
                trim(form.getPickupDate()));

        form.setPickupTime(
                trim(form.getPickupTime()));

        require(
                form.getCustomerName(),
                "氏名を入力してください。");

        if (form.getCustomerName().length() > 100) {
            throw new IllegalArgumentException(
                    "氏名は100文字以内で入力してください。");
        }

        validateFulfillmentMethod(
                form.getFulfillmentMethod());

        if ("DELIVERY".equals(
                form.getFulfillmentMethod())) {

            require(
                    form.getPostalCode(),
                    "郵便番号を入力してください。");

            if (form.getPostalCode().length() > 20) {
                throw new IllegalArgumentException(
                        "郵便番号は20文字以内で入力してください。");
            }

            require(
                    form.getAddress(),
                    "住所を入力してください。");

            if (form.getAddress().length() > 255) {
                throw new IllegalArgumentException(
                        "住所は255文字以内で入力してください。");
            }

            form.setPickupDate("");
            form.setPickupTime("");

        } else {
            validatePickupDateTime(form);
            form.setPostalCode("");
            form.setAddress("");
        }

        require(
                form.getPhone(),
                "電話番号を入力してください。");

        if (form.getPhone().length() > 20) {
            throw new IllegalArgumentException(
                    "電話番号は20文字以内で入力してください。");
        }

        require(
                form.getEmail(),
                "メールアドレスを入力してください。");

        if (form.getEmail().length() > 255) {
            throw new IllegalArgumentException(
                    "メールアドレスは255文字以内で入力してください。");
        }

        if (!form.getEmail().contains("@")) {
            throw new IllegalArgumentException(
                    "メールアドレスの形式が正しくありません。");
        }

        require(
                form.getPaymentMethod(),
                "支払方法を選択してください。");

        Set<String> availablePaymentMethods =
                "PICKUP".equals(
                        form.getFulfillmentMethod())
                        ? PICKUP_PAYMENT_METHODS
                        : DELIVERY_PAYMENT_METHODS;

        if (!availablePaymentMethods.contains(
                form.getPaymentMethod())) {

            throw new IllegalArgumentException(
                    "支払方法が正しくありません。");
        }
    }

    public BigDecimal calculateTotal(
            List<CartItem> cartItems) {

        if (cartItems == null) {
            return BigDecimal.ZERO;
        }

        return cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);
    }

    public String createCartSignature(
            List<CartItem> cartItems,
            String fulfillmentMethod) {

        validateCartItems(
                cartItems,
                fulfillmentMethod);

        return fulfillmentMethod + "|"
                + cartItems.stream()
                .sorted(
                        Comparator.comparingLong(
                                item ->
                                        item.getProduct()
                                                .getProductId()))
                .map(item ->
                        item.getProduct().getProductId()
                        + ":"
                        + item.getProduct().getPrice()
                                .toPlainString()
                        + ":"
                        + item.getQuantity())
                .collect(
                        Collectors.joining(";"));
    }

    public String createCartSignature(
            List<CartItem> cartItems) {

        return createCartSignature(
                cartItems,
                "DELIVERY");
    }

    /**
     * DB処理をcommitした後に注文完了メールを送信します。
     *
     * メール送信に失敗しても、確定済み注文は取り消しません。
     */
    public OrderCompletionResult placeOrder(
            String memberId,
            CheckoutForm form,
            String checkoutToken,
            String expectedCartSignature) {

        validateMemberId(memberId);
        validateAndNormalize(form);
        validateCheckoutToken(checkoutToken);

        require(
                expectedCartSignature,
                "購入内容の確認情報がありません。"
                + "もう一度確認画面から進んでください。");

        long shoppingOrderId;
        ShoppingOrder completedOrder;
        List<CartItem> completedItems;

        try (Connection conn =
                productDAO.openConnection()) {

            conn.setAutoCommit(false);

            try {
                Long cartId =
                        cartDAO
                                .findCartIdByMemberId(
                                        conn,
                                        memberId);

                if (cartId == null) {
                    throw new IllegalStateException(
                            "カートが見つかりません。");
                }

                List<CartItem> cartItems =
                        cartDAO.findCartItems(
                                conn,
                                memberId);

                String currentFulfillmentMethod =
                        cartDAO.findFulfillmentMethod(
                                conn,
                                cartId);

                if (!form.getFulfillmentMethod().equals(
                        currentFulfillmentMethod)) {

                    throw new IllegalStateException(
                            "確認後にカートの受取方法が変更されました。");
                }

                validateCartItems(
                        cartItems,
                        currentFulfillmentMethod);

                String currentSignature =
                        createCartSignature(
                                cartItems,
                                currentFulfillmentMethod);

                if (!expectedCartSignature.equals(
                        currentSignature)) {

                    throw new IllegalStateException(
                            "確認後に商品・価格・数量が変更されました。"
                            + "カート内容を確認し直してください。");
                }

                BigDecimal totalAmount =
                        calculateTotal(
                                cartItems);

                ShoppingOrder order =
                        createShoppingOrder(
                                memberId,
                                form,
                                totalAmount,
                                checkoutToken);

                shoppingOrderId =
                        shoppingOrderDAO.insert(
                                conn,
                                order);

                for (CartItem cartItem
                        : cartItems) {

                    long productId =
                            cartItem.getProduct()
                                    .getProductId();

                    int quantity =
                            cartItem.getQuantity();

                    boolean stockUpdated =
                            productDAO.decreaseStock(
                                    conn,
                                    productId,
                                    quantity);

                    if (!stockUpdated) {
                        throw new IllegalStateException(
                                "在庫不足または取扱停止の商品があります。"
                                + "カート内容を確認してください。");
                    }

                    ShoppingOrderItem orderItem =
                            createShoppingOrderItem(
                                    shoppingOrderId,
                                    cartItem);

                    if (!shoppingOrderItemDAO
                            .insert(
                                    conn,
                                    orderItem)) {

                        throw new IllegalStateException(
                                "注文明細を登録できませんでした。");
                    }
                }

                if (!cartDAO.clearCart(
                        conn,
                        cartId)) {

                    throw new IllegalStateException(
                            "カートをクリアできませんでした。");
                }

                conn.commit();

                order.setShoppingOrderId(
                        shoppingOrderId);

                completedOrder = order;
                completedItems =
                        List.copyOf(
                                cartItems);

            } catch (Exception e) {
                logger.error(
                        "【注文確定エラー】placeOrder()で例外が発生しました。",
                        e);
                rollback(conn, e);

                if (e instanceof
                        IllegalArgumentException
                        illegalArgumentException) {

                    throw illegalArgumentException;
                }

                if (e instanceof
                        IllegalStateException
                        illegalStateException) {

                    throw illegalStateException;
                }

                if (e instanceof SQLException
                        sqlException
                        && "23505".equals(
                                sqlException
                                        .getSQLState())) {

                    throw new IllegalStateException(
                            "この注文は既に処理されています。",
                            sqlException);
                }

                throw new IllegalStateException(
                        "注文処理中にエラーが発生しました。",
                        e);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "注文処理用のDB接続に失敗しました。",
                    e);
        }

        boolean mailSent = false;

        try {
            mailSent =
                    orderMailService
                            .sendOrderConfirmation(
                                    shoppingOrderId,
                                    completedOrder,
                                    completedItems);

        } catch (RuntimeException e) {

            logger.warn(
                    "注文完了メールの送信に失敗しました。"
                    + "注文ID={}",
                    shoppingOrderId,
                    e);
        }

        return new OrderCompletionResult(
                shoppingOrderId,
                mailSent);
    }

    public String getPaymentMethodLabel(
            String paymentMethod) {

        ShoppingOrder order =
                new ShoppingOrder();

        order.setPaymentMethod(
                paymentMethod);

        return order.getPaymentMethodLabel();
    }

    public String getFulfillmentMethodLabel(
            String fulfillmentMethod) {

        return "PICKUP".equals(fulfillmentMethod)
                ? "店頭受取"
                : "通販";
    }

    private ShoppingOrder createShoppingOrder(
            String memberId,
            CheckoutForm form,
            BigDecimal totalAmount,
            String checkoutToken) {

        ShoppingOrder order =
                new ShoppingOrder();

        order.setMemberId(memberId);
        order.setCustomerName(
                form.getCustomerName());
        order.setPostalCode(
                form.getPostalCode());
        order.setAddress(
                form.getAddress());
        order.setPhone(
                form.getPhone());
        order.setEmail(
                form.getEmail());
        order.setPaymentMethod(
                form.getPaymentMethod());
        order.setTotalAmount(
                totalAmount);
        order.setOrderStatus(
                "ORDERED");
        order.setFulfillmentMethod(
                form.getFulfillmentMethod());

        if ("PICKUP".equals(
                form.getFulfillmentMethod())) {

            order.setPickupDate(
                    LocalDate.parse(
                            form.getPickupDate()));
            order.setPickupTime(
                    LocalTime.parse(
                            form.getPickupTime()));
        }
        order.setCheckoutToken(
                checkoutToken);

        return order;
    }

    private ShoppingOrderItem
            createShoppingOrderItem(
                    long shoppingOrderId,
                    CartItem cartItem) {

        ShoppingOrderItem item =
                new ShoppingOrderItem();

        item.setShoppingOrderId(
                shoppingOrderId);

        item.setProductId(
                cartItem.getProduct()
                        .getProductId());

        item.setProductName(
                cartItem.getProduct()
                        .getProductName());

        item.setUnitPrice(
                cartItem.getProduct()
                        .getPrice());

        item.setQuantity(
                cartItem.getQuantity());

        return item;
    }

    private void validateCartItems(
            List<CartItem> cartItems,
            String fulfillmentMethod) {

        validateFulfillmentMethod(fulfillmentMethod);

        if (cartItems == null
                || cartItems.isEmpty()) {

            throw new IllegalStateException(
                    "カートに商品がありません。");
        }

        for (CartItem item : cartItems) {

            if (item == null
                    || item.getProduct() == null) {

                throw new IllegalStateException(
                        "カートの商品情報が正しくありません。");
            }

            if (!item.getProduct().isActive()) {
                throw new IllegalStateException(
                        "取扱停止中の商品があります。"
                        + "カート内容を確認してください。");
            }

            if ("DELIVERY".equals(fulfillmentMethod)
                    && !item.getProduct()
                            .isDeliveryAvailable()) {

                throw new IllegalStateException(
                        "通販に対応していない商品があります。");
            }

            if ("PICKUP".equals(fulfillmentMethod)
                    && !item.getProduct()
                            .isPickupAvailable()) {

                throw new IllegalStateException(
                        "店頭受取に対応していない商品があります。");
            }

            if (item.getQuantity() <= 0) {
                throw new IllegalStateException(
                        "数量が正しくない商品があります。");
            }

            if (item.getProduct().getStock() <= 0) {
                throw new IllegalStateException(
                        "在庫切れの商品があります。"
                        + "カート内容を確認してください。");
            }

            if (item.getQuantity()
                    > item.getProduct().getStock()) {

                throw new IllegalStateException(
                        "在庫数を超えている商品があります。"
                        + "カート内容を確認してください。");
            }
        }
    }

    private void validateCheckoutToken(
            String checkoutToken) {

        require(
                checkoutToken,
                "注文確認用トークンがありません。"
                + "もう一度確認画面から進んでください。");

        if (checkoutToken.length() > 36) {
            throw new IllegalArgumentException(
                    "注文確認用トークンが正しくありません。");
        }
    }

    private void validateFulfillmentMethod(
            String fulfillmentMethod) {

        if (!VALID_FULFILLMENT_METHODS.contains(
                fulfillmentMethod)) {

            throw new IllegalArgumentException(
                    "受取方法が正しくありません。");
        }
    }

    private void validatePickupDateTime(
            CheckoutForm form) {

        require(
                form.getPickupDate(),
                "受取希望日を入力してください。");

        require(
                form.getPickupTime(),
                "受取希望時間を入力してください。");

        try {
            LocalDate pickupDate =
                    LocalDate.parse(
                            form.getPickupDate());

            LocalTime pickupTime =
                    LocalTime.parse(
                            form.getPickupTime());

            if (LocalDateTime.of(
                    pickupDate,
                    pickupTime)
                    .isBefore(LocalDateTime.now())) {

                throw new IllegalArgumentException(
                        "受取希望日時は現在より後を指定してください。");
            }

        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "受取希望日時の形式が正しくありません。",
                    e);
        }
    }

    private void rollback(
            Connection conn,
            Exception originalException) {

        try {
            conn.rollback();

        } catch (SQLException rollbackException) {
            originalException.addSuppressed(
                    rollbackException);
        }
    }

    private void validateMemberId(
            String memberId) {

        if (memberId == null
                || memberId.isBlank()) {

            throw new IllegalArgumentException(
                    "会員IDが指定されていません。");
        }
    }

    private void require(
            String value,
            String message) {

        if (value == null
                || value.isBlank()) {

            throw new IllegalArgumentException(
                    message);
        }
    }

    private String trim(
            String value) {

        return value == null
                ? ""
                : value.trim();
    }

    private String emptyIfNull(
            String value) {

        return value == null
                ? ""
                : value;
    }
}

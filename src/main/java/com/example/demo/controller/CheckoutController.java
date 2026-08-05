package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CartItem;
import com.example.demo.model.CheckoutForm;
import com.example.demo.model.Member;
import com.example.demo.model.OrderCompletionResult;
import com.example.demo.service.CheckoutService;

import lombok.RequiredArgsConstructor;

/**
 * 注文者情報入力・購入確認・注文確定を担当します。
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private static final String
            PENDING_CHECKOUT =
                    "pendingCheckout";

    private static final String
            PENDING_CART_SIGNATURE =
                    "pendingCartSignature";

    private static final String
            PENDING_CHECKOUT_VALUES =
                    "pendingCheckoutValues";

    private final CheckoutService
            checkoutService;

    @GetMapping("/input")
    public String showInput(
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        try {
            List<CartItem> cartItems =
                    checkoutService
                            .getCartItemsForCheckout(
                                    loginMember.getMemberId());

            String fulfillmentMethod =
                    checkoutService
                            .getCartFulfillmentMethod(
                                    loginMember.getMemberId());

            CheckoutForm checkoutForm =
                    getPendingCheckout(session);

            if (checkoutForm == null) {
                checkoutForm =
                        checkoutService
                                .createInitialForm(
                                        loginMember.getMemberId());
            }

            checkoutForm.setFulfillmentMethod(
                    fulfillmentMethod);

            addCheckoutSummary(
                    model,
                    cartItems);

            model.addAttribute(
                    "checkoutForm",
                    checkoutForm);

            return "checkoutInput";

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "cartErrorMessage",
                    e.getMessage());

            return "redirect:/cart";
        }
    }

    @PostMapping("/confirm")
    public String confirm(
            @ModelAttribute
            CheckoutForm checkoutForm,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        List<CartItem> cartItems;

        try {
            cartItems =
                    checkoutService
                            .getCartItemsForCheckout(
                                    loginMember.getMemberId());

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "cartErrorMessage",
                    e.getMessage());

            return "redirect:/cart";
        }

        try {
            checkoutForm.setFulfillmentMethod(
                    checkoutService
                            .getCartFulfillmentMethod(
                                    loginMember.getMemberId()));

            checkoutService
                    .validateAndNormalize(
                            checkoutForm);

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "errorMessage",
                    e.getMessage());

            model.addAttribute(
                    "checkoutForm",
                    checkoutForm);

            addCheckoutSummary(
                    model,
                    cartItems);

            return "checkoutInput";
        }

        String checkoutToken =
                UUID.randomUUID()
                        .toString();

        String cartSignature =
                checkoutService
                        .createCartSignature(
                                cartItems,
                                checkoutForm
                                        .getFulfillmentMethod());

        session.setAttribute(
                PENDING_CHECKOUT,
                checkoutForm);

        session.setAttribute(
                PENDING_CART_SIGNATURE,
                cartSignature);

        storePendingCheckoutValues(
                session,
                checkoutForm);

        addCheckoutSummary(
                model,
                cartItems);

        model.addAttribute(
                "checkoutForm",
                checkoutForm);

        model.addAttribute(
                "checkoutToken",
                checkoutToken);

        model.addAttribute(
                "cartSignature",
                cartSignature);

        model.addAttribute(
                "paymentMethodLabel",
                checkoutService
                        .getPaymentMethodLabel(
                                checkoutForm
                                        .getPaymentMethod()));

        model.addAttribute(
                "fulfillmentMethodLabel",
                checkoutService
                        .getFulfillmentMethodLabel(
                                checkoutForm
                                        .getFulfillmentMethod()));

        return "checkoutConfirm";
    }

    @PostMapping("/complete")
    public String complete(
            @ModelAttribute
            CheckoutForm postedCheckoutForm,
            @RequestParam("checkoutToken")
            String checkoutToken,
            @RequestParam(
                    value = "cartSignature",
                    required = false)
            String postedCartSignature,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        try {
            CheckoutForm checkoutForm =
                    getPendingCheckout(session);

            if (!hasCustomerName(checkoutForm)) {
                checkoutForm =
                        getPendingCheckoutValues(
                                session);
            }

            if (!hasCustomerName(checkoutForm)) {
                checkoutForm = postedCheckoutForm;
            }

            String fulfillmentMethod =
                    checkoutService
                            .getCartFulfillmentMethod(
                                    loginMember.getMemberId());

            checkoutForm.setFulfillmentMethod(
                    fulfillmentMethod);

            String expectedCartSignature =
                    postedCartSignature;

            if (expectedCartSignature == null
                    || expectedCartSignature.isBlank()) {

                expectedCartSignature =
                        getSessionString(
                                session,
                                PENDING_CART_SIGNATURE);
            }

            if (expectedCartSignature == null
                    || expectedCartSignature.isBlank()) {

                List<CartItem> currentCartItems =
                        checkoutService
                                .getCartItemsForCheckout(
                                        loginMember.getMemberId());

                expectedCartSignature =
                        checkoutService
                                .createCartSignature(
                                        currentCartItems,
                                        fulfillmentMethod);
            }

            OrderCompletionResult result =
                    checkoutService.placeOrder(
                            loginMember.getMemberId(),
                            checkoutForm,
                            checkoutToken,
                            expectedCartSignature);

            clearPendingCheckout(session);

            redirectAttributes.addFlashAttribute(
                    "completedOrderId",
                    result.getShoppingOrderId());

            redirectAttributes.addFlashAttribute(
                    "mailSent",
                    result.isMailSent());

            redirectAttributes.addFlashAttribute(
                    "fulfillmentMethod",
                    checkoutForm.getFulfillmentMethod());

            redirectAttributes.addFlashAttribute(
                    "pickupDate",
                    checkoutForm.getPickupDate());

            redirectAttributes.addFlashAttribute(
                    "pickupTime",
                    checkoutForm.getPickupTime());

            return "redirect:/checkout/complete";

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "cartErrorMessage",
                    e.getMessage());

            return "redirect:/cart";
        }
    }

    @GetMapping("/complete")
    public String showComplete(
            Model model) {

        if (!model.containsAttribute(
                "completedOrderId")) {

            return "redirect:/menu";
        }

        return "ThankyouShop";
    }

    private void addCheckoutSummary(
            Model model,
            List<CartItem> cartItems) {

        BigDecimal total =
                checkoutService
                        .calculateTotal(
                                cartItems);

        model.addAttribute(
                "cartList",
                cartItems);

        model.addAttribute(
                "total",
                total);
    }

    private CheckoutForm getPendingCheckout(
            HttpSession session) {

        Object pendingCheckout =
                session.getAttribute(
                        PENDING_CHECKOUT);

        if (pendingCheckout
                instanceof CheckoutForm form) {

            return form;
        }

        return null;
    }

    private String getSessionString(
            HttpSession session,
            String attributeName) {

        Object value =
                session.getAttribute(
                        attributeName);

        return value instanceof String string
                ? string
                : null;
    }

    private void storePendingCheckoutValues(
            HttpSession session,
            CheckoutForm form) {

        Map<String, String> values =
                new HashMap<>();

        values.put(
                "customerName",
                form.getCustomerName());
        values.put(
                "postalCode",
                form.getPostalCode());
        values.put(
                "address",
                form.getAddress());
        values.put(
                "phone",
                form.getPhone());
        values.put(
                "email",
                form.getEmail());
        values.put(
                "paymentMethod",
                form.getPaymentMethod());
        values.put(
                "fulfillmentMethod",
                form.getFulfillmentMethod());
        values.put(
                "pickupDate",
                form.getPickupDate());
        values.put(
                "pickupTime",
                form.getPickupTime());

        session.setAttribute(
                PENDING_CHECKOUT_VALUES,
                values);
    }

    private CheckoutForm getPendingCheckoutValues(
            HttpSession session) {

        Object storedValues =
                session.getAttribute(
                        PENDING_CHECKOUT_VALUES);

        if (!(storedValues instanceof Map<?, ?> values)) {
            return null;
        }

        CheckoutForm form =
                new CheckoutForm();

        form.setCustomerName(
                getMapString(
                        values,
                        "customerName"));
        form.setPostalCode(
                getMapString(
                        values,
                        "postalCode"));
        form.setAddress(
                getMapString(
                        values,
                        "address"));
        form.setPhone(
                getMapString(
                        values,
                        "phone"));
        form.setEmail(
                getMapString(
                        values,
                        "email"));
        form.setPaymentMethod(
                getMapString(
                        values,
                        "paymentMethod"));
        form.setFulfillmentMethod(
                getMapString(
                        values,
                        "fulfillmentMethod"));
        form.setPickupDate(
                getMapString(
                        values,
                        "pickupDate"));
        form.setPickupTime(
                getMapString(
                        values,
                        "pickupTime"));

        return form;
    }

    private String getMapString(
            Map<?, ?> values,
            String key) {

        Object value = values.get(key);

        return value instanceof String string
                ? string
                : "";
    }

    private boolean hasCustomerName(
            CheckoutForm form) {

        return form != null
                && form.getCustomerName() != null
                && !form.getCustomerName().isBlank();
    }

    private void clearPendingCheckout(
            HttpSession session) {

        session.removeAttribute(
                PENDING_CHECKOUT);

        session.removeAttribute(
                PENDING_CART_SIGNATURE);

        session.removeAttribute(
                PENDING_CHECKOUT_VALUES);
    }

    private Member getLoginMember(
            HttpSession session) {

        Object loginUser =
                session.getAttribute("loginUser");

        if (loginUser instanceof Member member) {
            return member;
        }

        Object loginMember =
                session.getAttribute("loginMember");

        if (loginMember instanceof Member member) {
            return member;
        }

        return null;
    }
}
package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;
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

import com.example.demo.model.CartItem_oonaka;
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
            PENDING_CHECKOUT_TOKEN =
                    "pendingCheckoutToken";

    private static final String
            PENDING_CART_SIGNATURE =
                    "pendingCartSignature";

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

        session.removeAttribute(
                PENDING_CHECKOUT_TOKEN);

        session.removeAttribute(
                PENDING_CART_SIGNATURE);

        try {
            List<CartItem_oonaka> cartItems =
                    checkoutService
                            .getCartItemsForCheckout(
                                    loginMember.getMemberId());

            CheckoutForm checkoutForm =
                    getPendingCheckout(session);

            if (checkoutForm == null) {
                checkoutForm =
                        checkoutService
                                .createInitialForm(
                                        loginMember.getMemberId());
            }

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

        List<CartItem_oonaka> cartItems;

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
                                cartItems);

        session.setAttribute(
                PENDING_CHECKOUT,
                checkoutForm);

        session.setAttribute(
                PENDING_CHECKOUT_TOKEN,
                checkoutToken);

        session.setAttribute(
                PENDING_CART_SIGNATURE,
                cartSignature);

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
                "paymentMethodLabel",
                checkoutService
                        .getPaymentMethodLabel(
                                checkoutForm
                                        .getPaymentMethod()));

        return "checkoutConfirm";
    }

    @PostMapping("/complete")
    public String complete(
            @RequestParam("checkoutToken")
            String checkoutToken,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Member loginMember =
                getLoginMember(session);

        if (loginMember == null) {
            return "redirect:/";
        }

        CheckoutForm checkoutForm =
                getPendingCheckout(session);

        String sessionToken =
                getSessionString(
                        session,
                        PENDING_CHECKOUT_TOKEN);

        String expectedCartSignature =
                getSessionString(
                        session,
                        PENDING_CART_SIGNATURE);

        if (checkoutForm == null
                || sessionToken == null
                || expectedCartSignature == null
                || !sessionToken.equals(
                        checkoutToken)) {

            redirectAttributes.addFlashAttribute(
                    "cartErrorMessage",
                    "注文確認情報が失効しました。"
                    + "もう一度カートから進んでください。");

            return "redirect:/cart";
        }

        try {
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
            List<CartItem_oonaka> cartItems) {

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

    private void clearPendingCheckout(
            HttpSession session) {

        session.removeAttribute(
                PENDING_CHECKOUT);

        session.removeAttribute(
                PENDING_CHECKOUT_TOKEN);

        session.removeAttribute(
                PENDING_CART_SIGNATURE);
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
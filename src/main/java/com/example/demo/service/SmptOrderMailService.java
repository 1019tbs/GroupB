//package com.example.demo.service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.model.CartItem_oonaka;
//import com.example.demo.model.ShoppingOrder;
//
//import lombok.RequiredArgsConstructor;
//
///**
// * SMTPを使って注文完了メールを送信します。
// */
//@Service
//@RequiredArgsConstructor
//@ConditionalOnProperty(
//        name = "app.mail.enabled",
//        havingValue = "true")
//public class SmtpOrderMailService
//        implements OrderMailService {
//
//    private final JavaMailSender
//            javaMailSender;
//
//    @Value("${app.mail.from:}")
//    private String fromAddress;
//
//    @Override
//    public boolean sendOrderConfirmation(
//            long shoppingOrderId,
//            ShoppingOrder order,
//            List<CartItem_oonaka> cartItems) {
//
//        if (order == null
//                || order.getEmail() == null
//                || order.getEmail().isBlank()) {
//
//            return false;
//        }
//
//        SimpleMailMessage message =
//                new SimpleMailMessage();
//
//        if (fromAddress != null
//                && !fromAddress.isBlank()) {
//
//            message.setFrom(
//                    fromAddress);
//        }
//
//        message.setTo(
//                order.getEmail());
//
//        message.setSubject(
//                "【Honey Bloom】ご注文を受け付けました"
//                + "（注文番号："
//                + shoppingOrderId
//                + "）");
//
//        message.setText(
//                createBody(
//                        shoppingOrderId,
//                        order,
//                        cartItems));
//
//        javaMailSender.send(
//                message);
//
//        return true;
//    }
//
//    private String createBody(
//            long shoppingOrderId,
//            ShoppingOrder order,
//            List<CartItem_oonaka> cartItems) {
//
//        StringBuilder body =
//                new StringBuilder();
//
//        body.append(
//                order.getCustomerName())
//            .append(" 様")
//            .append(System.lineSeparator())
//            .append(System.lineSeparator());
//
//        body.append(
//                "Honey Bloomをご利用いただき、"
//                + "ありがとうございます。")
//            .append(System.lineSeparator());
//
//        body.append(
//                "以下の内容でご注文を受け付けました。")
//            .append(System.lineSeparator())
//            .append(System.lineSeparator());
//
//        body.append("注文番号：")
//            .append(shoppingOrderId)
//            .append(System.lineSeparator());
//
//        body.append(System.lineSeparator())
//            .append("【ご注文商品】")
//            .append(System.lineSeparator());
//
//        for (CartItem_oonaka item
//                : cartItems) {
//
//            body.append("・")
//                .append(
//                        item.getProduct()
//                                .getProductName())
//                .append(" × ")
//                .append(
//                        item.getQuantity())
//                .append("個　")
//                .append(
//                        item.getSubtotal()
//                                .toPlainString())
//                .append("円")
//                .append(
//                        System.lineSeparator());
//        }
//
//        BigDecimal total =
//                cartItems.stream()
//                        .map(
//                                CartItem_oonaka
//                                        ::getSubtotal)
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add);
//
//        body.append(System.lineSeparator())
//            .append("合計金額：")
//            .append(
//                    total.toPlainString())
//            .append("円")
//            .append(System.lineSeparator())
//            .append(System.lineSeparator());
//
//        body.append("【注文者情報】")
//            .append(System.lineSeparator())
//            .append("氏名：")
//            .append(
//                    order.getCustomerName())
//            .append(System.lineSeparator())
//            .append("郵便番号：")
//            .append(
//                    order.getPostalCode())
//            .append(System.lineSeparator())
//            .append("住所：")
//            .append(
//                    order.getAddress())
//            .append(System.lineSeparator())
//            .append("電話番号：")
//            .append(
//                    order.getPhone())
//            .append(System.lineSeparator())
//            .append("支払方法：")
//            .append(
//                    order.getPaymentMethodLabel())
//            .append(System.lineSeparator())
//            .append(System.lineSeparator());
//
//        body.append(
//                "このメールは注文受付時に"
//                + "自動送信されています。");
//
//        return body.toString();
//    }
//}
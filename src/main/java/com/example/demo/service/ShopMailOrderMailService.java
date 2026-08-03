package com.example.demo.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.example.demo.model.CartItem;
import com.example.demo.model.ShoppingOrder;

import shopMail.ShopMail;

@Service
public class ShopMailOrderMailService
        implements OrderMailService {

    private static final int GROUP_NUMBER = 2;
    private static final int HTML_FORMAT = 1;

    @Override
    public boolean sendOrderConfirmation(
            long shoppingOrderId,
            ShoppingOrder order,
            List<CartItem> cartItems) {

        String subject =
                order.isPickup()
                ? "【Honey Bloom】店頭受取のご予約を受け付けました"
                : "【Honey Bloom】ご注文を受け付けました";

        String body =
                createHtmlBody(
                        shoppingOrderId,
                        order,
                        cartItems);

        try {
            ShopMail.send(
                    GROUP_NUMBER,
                    order.getEmail(),
                    "Honey Bloom",
                    subject,
                    body,
                    HTML_FORMAT);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String createHtmlBody(
            long shoppingOrderId,
            ShoppingOrder order,
            List<CartItem> cartItems) {

        StringBuilder body = new StringBuilder();

        body.append("<html>");
        body.append("<body style=\"font-family: sans-serif;\">");

        body.append(order.isPickup()
                ? "<h2>店頭受取のご予約ありがとうございます</h2>"
                : "<h2>ご注文ありがとうございます</h2>");

        body.append("<p>");
        body.append(escapeHtml(order.getCustomerName()));
        body.append(" 様</p>");

        body.append("<p>");
        body.append(order.isPickup()
                ? "以下の内容で店頭受取のご予約を受け付けました。"
                : "以下の内容でご注文を受け付けました。");
        body.append("</p>");

        body.append("<p><strong>受取方法：</strong>");
        body.append(escapeHtml(
                order.getFulfillmentMethodLabel()));
        body.append("</p>");

        if (order.isPickup()) {
            body.append("<p><strong>受取日時：</strong>");
            body.append(escapeHtml(
                    order.getPickupDateText()));
            body.append(" ");
            body.append(escapeHtml(
                    order.getPickupTimeText()));
            body.append("</p>");
        }

        body.append("<p><strong>支払方法：</strong>");
        body.append(escapeHtml(
                order.getPaymentMethodLabel()));
        body.append("</p>");

        body.append("<p>");
        body.append("<strong>注文番号：</strong>");
        body.append(shoppingOrderId);
        body.append("</p>");

        body.append("""
                <table style="border-collapse: collapse;">
                    <tr>
                        <th style="border: 1px solid #ccc; padding: 8px;">
                            商品名
                        </th>
                        <th style="border: 1px solid #ccc; padding: 8px;">
                            単価
                        </th>
                        <th style="border: 1px solid #ccc; padding: 8px;">
                            数量
                        </th>
                        <th style="border: 1px solid #ccc; padding: 8px;">
                            小計
                        </th>
                    </tr>
                """);

        for (CartItem item : cartItems) {

            body.append("<tr>");

            body.append("<td style=\"border: 1px solid #ccc; padding: 8px;\">");
            body.append(
                    escapeHtml(
                            item.getProduct()
                                    .getProductName()));
            body.append("</td>");

            body.append("<td style=\"border: 1px solid #ccc; padding: 8px;\">");
            body.append(formatPrice(
                    item.getProduct().getPrice()));
            body.append("</td>");

            body.append("<td style=\"border: 1px solid #ccc; padding: 8px;\">");
            body.append(item.getQuantity());
            body.append("</td>");

            body.append("<td style=\"border: 1px solid #ccc; padding: 8px;\">");
            body.append(formatPrice(
                    item.getSubtotal()));
            body.append("</td>");

            body.append("</tr>");
        }

        body.append("</table>");

        body.append("<p style=\"font-size: 18px; font-weight: bold;\">");
        body.append("合計金額：");
        body.append(formatPrice(
                order.getTotalAmount()));
        body.append("</p>");

        if (order.isPickup()) {
            body.append("<p>ご指定の日時に店舗へお越しください。</p>");
        }

        body.append("<p>");
        body.append("Honey Bloomをご利用いただき、");
        body.append("ありがとうございました。");
        body.append("</p>");

        body.append("</body>");
        body.append("</html>");

        return body.toString();
    }

    private String escapeHtml(
            String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
    private String formatPrice(
    		BigDecimal price) {
    	
    	if (price == null) {
    		return "0円";
    	}
    	
    	NumberFormat formatter =
    			NumberFormat.getNumberInstance(
    					Locale.JAPAN);
    	
    	return formatter.format(price) + "円";
    }
}

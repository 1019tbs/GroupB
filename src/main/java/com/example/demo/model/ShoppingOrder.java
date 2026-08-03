package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ショッピング注文の注文者情報・合計金額・明細を保持します。
 *
 * 既存の単品予約用Orderとは別のモデルです。
 */
@Getter
@Setter
@NoArgsConstructor
public class ShoppingOrder {

    private static final DateTimeFormatter
            DISPLAY_DATE_TIME =
                    DateTimeFormatter.ofPattern(
                            "yyyy/MM/dd HH:mm");

    private Long shoppingOrderId;
    private String memberId;
    private String customerName;
    private String postalCode;
    private String address;
    private String phone;
    private String email;
    private String paymentMethod;
    private BigDecimal totalAmount;
    private String orderStatus;
    private String fulfillmentMethod;
    private LocalDate pickupDate;
    private LocalTime pickupTime;
    private String checkoutToken;
    private LocalDateTime createdAt;

    private List<ShoppingOrderItem> items =
            new ArrayList<>();

    public String getCreatedAtText() {

        if (createdAt == null) {
            return "";
        }

        return createdAt.format(
                DISPLAY_DATE_TIME);
    }

    public String getPaymentMethodLabel() {

        if ("credit".equals(paymentMethod)) {
            return "クレジットカード";
        }

        if ("bank".equals(paymentMethod)) {
            return "銀行振込";
        }

        if ("cash_on_delivery".equals(
                paymentMethod)) {
            return "代金引換";
        }

        if ("convenience_store".equals(
                paymentMethod)) {
            return "コンビニ払い";
        }

        if ("pay_at_store".equals(
                paymentMethod)) {
            return "店頭支払い";
        }

        return paymentMethod == null
                ? ""
                : paymentMethod;
    }

    public String getOrderStatusLabel() {

        if ("ORDERED".equals(orderStatus)) {
            return isPickup()
                    ? "予約受付済み"
                    : "注文済み";
        }

        if ("CANCELLED".equals(orderStatus)) {
            return "キャンセル";
        }

        if ("COMPLETED".equals(orderStatus)) {
            return "完了";
        }

        return orderStatus == null
                ? ""
                : orderStatus;
    }

    public boolean isPickup() {
        return "PICKUP".equals(fulfillmentMethod);
    }

    public boolean isDelivery() {
        return "DELIVERY".equals(fulfillmentMethod);
    }

    public String getFulfillmentMethodLabel() {
        if (isPickup()) {
            return "店頭受取";
        }
        if (isDelivery()) {
            return "通販";
        }
        return fulfillmentMethod == null
                ? ""
                : fulfillmentMethod;
    }

    public String getPickupDateText() {
        return pickupDate == null
                ? ""
                : pickupDate.format(
                        DateTimeFormatter.ofPattern(
                                "yyyy/MM/dd"));
    }

    public String getPickupTimeText() {
        return pickupTime == null
                ? ""
                : pickupTime.format(
                        DateTimeFormatter.ofPattern(
                                "HH:mm"));
    }
}

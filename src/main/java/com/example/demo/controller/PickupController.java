package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Order;
import com.example.demo.service.PickupService;

@Controller
public class PickupController {

    private final PickupService pickupService;

    public PickupController(
            PickupService pickupService) {

        this.pickupService = pickupService;
    }

    /*
     * 予約入力画面から受け取り、
     * 予約内容確認画面を表示する
     */
    @PostMapping("/pickup/start")
    public String startPickup(

            @RequestParam String customerName,
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam String pickupDate,
            @RequestParam String pickupTime,
            @RequestParam String email,
            @RequestParam String phone,
            Model model) {

        model.addAttribute(
                "customerName",
                customerName);

        model.addAttribute(
                "productId",
                productId);

        model.addAttribute(
                "quantity",
                quantity);

        /*
         * pickupConfirm.jspでは
         * reservationDate / reservationTime
         * という名前で表示しているため、
         * Model名はそのままにする
         */
        model.addAttribute(
                "reservationDate",
                pickupDate);

        model.addAttribute(
                "reservationTime",
                pickupTime);

        model.addAttribute(
                "email",
                email);

        model.addAttribute(
                "phone",
                phone);

        return "pickupConfirm";
    }

    /*
     * 予約内容確認画面から受け取り、
     * DBへ予約情報を登録する
     */
    @PostMapping("/pickup/complete")
    public String completePickup(

            @RequestParam String customerName,
            @RequestParam Long productId,
            @RequestParam String reservationDate,
            @RequestParam String reservationTime,
            @RequestParam String email,
            @RequestParam String phone,
            Model model) {

        Order order = new Order();

        order.setCustomerName(
                customerName);

        order.setProductId(
                productId);

        order.setReservationDate(
                LocalDate.parse(
                        reservationDate));

        order.setReservationTime(
                LocalTime.parse(
                        reservationTime));

        order.setEmail(
                email);

        order.setPhone(
                phone);

        boolean result =
                pickupService.reserve(order);

        if (!result) {

            model.addAttribute(
                    "errorMsg",
                    "予約の登録に失敗しました。");

            return "pickupConfirm";
        }

        model.addAttribute(
                "order",
                order);

        return "pickupComplete";
    }
}
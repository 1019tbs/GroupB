package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.FormContactDAO;
import com.example.demo.dao.OrderDAO;
import com.example.demo.model.FormContact;
import com.example.demo.model.Order;
import com.example.demo.service.InventoryService;

@Controller
public class FormController {

    @Autowired
    private FormContactDAO formContactDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private InventoryService inventoryService;

    /**
     * お問い合わせ画面表示
     */
    @GetMapping("/form")
    public String showForm() {
        return "form";
    }

    /**
     * お問い合わせ・予約送信
     */
    @PostMapping("/form/submit")
    public String submitForm(

            @RequestParam(name = "genre")
            String genre,

            @RequestParam(
                    name = "customerName",
                    required = false)
            String customerName,

            @RequestParam(
                    name = "email",
                    required = false)
            String email,

            @RequestParam(
                    name = "phone",
                    required = false)
            String phone,

            @RequestParam(
                    name = "subject",
                    required = false)
            String subject,

            @RequestParam(
                    name = "message",
                    required = false)
            String message,

            @RequestParam(
                    name = "menuId",
                    required = false)
            Integer menuId,

            @RequestParam(
                    name = "reservationDate",
                    required = false)
            String reservationDate,

            @RequestParam(
                    name = "reservationTime",
                    required = false)
            String reservationTime,

            Model model) {

        if (customerName == null
                || customerName.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "お名前を入力してください");

            return returnInputPage(genre, model);
        }

        if (email == null
                || email.isBlank()) {

            model.addAttribute(
                    "errorMsg",
                    "メールアドレスを入力してください");

            return returnInputPage(genre, model);
        }

        if ("contact".equals(genre)) {

            return saveContact(
                    customerName,
                    email,
                    phone,
                    subject,
                    message,
                    model);
        }

        if ("reservation".equals(genre)) {

            return saveReservation(
                    customerName,
                    email,
                    phone,
                    menuId,
                    reservationDate,
                    reservationTime,
                    model);
        }

        model.addAttribute(
                "errorMsg",
                "送信内容が正しくありません");

        return "form";
    }

    /**
     * お問い合わせ保存
     */
    private String saveContact(
            String customerName,
            String email,
            String phone,
            String subject,
            String message,
            Model model) {

        FormContact contact =
                new FormContact();

        contact.setCustomerName(
                customerName);

        contact.setEmail(
                email);

        contact.setPhone(
                phone);

        contact.setSubject(
                subject);

        contact.setMessage(
                message);

        if (!formContactDAO.insert(contact)) {

            model.addAttribute(
                    "errorMsg",
                    "保存に失敗しました");

            return "form";
        }

        model.addAttribute(
                "genre",
                "お問い合わせ");

        model.addAttribute(
                "customerName",
                customerName);

        return "ThankyouContact";
    }

    /**
     * 予約保存
     */
    private String saveReservation(
            String customerName,
            String email,
            String phone,
            Integer menuId,
            String reservationDate,
            String reservationTime,
            Model model) {

        try {

            /*
             * 予約項目の未入力確認
             */
            if (menuId == null) {

                model.addAttribute(
                        "errorMsg",
                        "商品を選択してください");

                return returnMenu(model);
            }

            if (reservationDate == null
                    || reservationDate.isBlank()) {

                model.addAttribute(
                        "errorMsg",
                        "予約日を入力してください");

                return returnMenu(model);
            }

            if (reservationTime == null
                    || reservationTime.isBlank()) {

                model.addAttribute(
                        "errorMsg",
                        "予約時間を入力してください");

                return returnMenu(model);
            }

            Order order =
                    new Order();

            order.setCustomerName(
                    customerName);

            order.setEmail(
                    email);

            order.setPhone(
                    phone);

            /*
             * 画面ではmenuIdという名前だが、
             * ordersテーブルではproduct_idとして保存する
             */
            order.setProductId(
                    menuId.longValue());

            order.setReservationDate(
                    LocalDate.parse(
                            reservationDate));

            order.setReservationTime(
                    LocalTime.parse(
                            reservationTime));

            if (!orderDAO.insert(order)) {

                model.addAttribute(
                        "errorMsg",
                        "予約保存に失敗しました");

                return returnMenu(model);
            }

            model.addAttribute(
                    "genre",
                    "予約");

            model.addAttribute(
                    "customerName",
                    customerName);

            return "ThankyouShop";

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "errorMsg",
                    "入力内容が正しくありません");

            return returnMenu(model);
        }
    }

    /**
     * エラー時の戻り先判定
     */
    private String returnInputPage(
            String genre,
            Model model) {

        if ("reservation".equals(genre)) {
            return returnMenu(model);
        }

        return "form";
    }

    /**
     * 予約入力エラー時に、取扱中の商品一覧を再取得して
     * メニュー画面へ戻します。
     */
    private String returnMenu(Model model) {

        model.addAttribute(
                "productList",
                inventoryService.findAllActive());

        return "menu";
    }
}
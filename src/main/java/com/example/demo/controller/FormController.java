package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.FormContactDAO;
import com.example.demo.dao.MenuDAO;
import com.example.demo.model.FormContact;
import com.example.demo.model.Member;
import com.example.demo.model.MenuList;

@Controller
public class FormController {

    @Autowired
    private FormContactDAO formContactDAO;

    @Autowired
    private MenuDAO menuDAO;

    /**
     * お問い合わせ画面表示
     */
    @GetMapping("/form")
    public String showForm() {
        return "form";
    }

    /**
     * メニュー・予約画面表示
     */
    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }

    /**
     * お問い合わせ・予約送信
     */
    
    @PostMapping("/form/submit")
    public String submitForm(

            @RequestParam(name = "genre") String genre,

            @RequestParam(name = "customerName", required = false)
            String customerName,

            @RequestParam(name = "email", required = false)
            String email,

            @RequestParam(name = "phone", required = false)
            String phone,

            @RequestParam(name = "subject", required = false)
            String subject,

            @RequestParam(name = "message", required = false)
            String message,

            @RequestParam(name = "menuId", required = false)
            Integer menuId,

            @RequestParam(name = "reservationDate", required = false)
            String reservationDate,

            @RequestParam(name = "reservationTime", required = false)
            String reservationTime,

            @RequestParam(name = "numberOfPeople", required = false)
            Integer numberOfPeople,

            Model model) {

        if (customerName == null || customerName.isBlank()) {
            model.addAttribute("errorMsg", "お名前を入力してください");
            return returnInputPage(genre);
        }

        if (email == null || email.isBlank()) {
            model.addAttribute("errorMsg", "メールアドレスを入力してください");
            return returnInputPage(genre);
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
                    numberOfPeople,
                    model);
        }

        model.addAttribute("errorMsg", "送信内容が正しくありません");
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

        FormContact contact = new FormContact();

        contact.setCustomerName(customerName);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setSubject(subject);
        contact.setMessage(message);

        if (!formContactDAO.insert(contact)) {

            model.addAttribute(
                    "errorMsg",
                    "保存に失敗しました");

            return "form";
        }

        model.addAttribute("genre", "お問い合わせ");
        model.addAttribute("customerName", customerName);

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
            Integer numberOfPeople,
            Model model) {

        try {

            MenuList reservation = new MenuList();

            reservation.setCustomerName(customerName);
            reservation.setEmail(email);
            reservation.setPhone(phone);
            reservation.setMenuId(menuId);

            reservation.setReservationDate(
                    LocalDate.parse(reservationDate));

            reservation.setReservationTime(
                    LocalTime.parse(reservationTime));

            reservation.setNumberOfPeople(numberOfPeople);

            if (!menuDAO.insertReservation(reservation)) {

                model.addAttribute(
                        "errorMsg",
                        "予約保存に失敗しました");

                return "menu";
            }

            model.addAttribute("genre", "予約");
            model.addAttribute("customerName", customerName);

            return "ThankyouShop";

        } catch (Exception e) {

            model.addAttribute(
                    "errorMsg",
                    "入力内容が正しくありません");

            return "menu";
        }
    }

    /**
     * 管理者画面
     */
    @GetMapping("/admin")
    public String showAdmin(
            HttpSession session,
            Model model) {

        Member loginMember =
                (Member) session.getAttribute("loginMember");

        if (loginMember == null) {

            model.addAttribute(
                    "errorMsg",
                    "ログインしてください");

            return "index";
        }

        if (!"admin".equals(loginMember.getRole())) {

            model.addAttribute(
                    "errorMsg",
                    "管理者のみ閲覧できます");

            return "main";
        }

        List<FormContact> contactList =
                formContactDAO.findAll();

        List<MenuList> reservationList =
                menuDAO.findAllReservations();

        model.addAttribute(
                "contactList",
                contactList);

        model.addAttribute(
                "reservationList",
                reservationList);

        return "admin";
    }

    /**
     * エラー時の戻り先判定
     */
    private String returnInputPage(String genre) {

        if ("reservation".equals(genre)) {
            return "menu";
        }

        return "form";
    }
}
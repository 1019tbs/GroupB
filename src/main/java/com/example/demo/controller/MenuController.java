package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.InventoryService;

import lombok.RequiredArgsConstructor;

/**
 * お客様向けの商品メニュー画面を担当します。
 */
@Controller
@RequiredArgsConstructor
public class MenuController {

    private final InventoryService inventoryService;

    /**
     * 取扱中の商品をDBから取得してメニュー画面を表示します。
     */
    @GetMapping("/menu")
    public String showMenu(Model model) {

        model.addAttribute(
                "productList",
                inventoryService.findAllActive());

        return "menu";
    }
}
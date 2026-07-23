package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public String showInventory(Model model) {

        model.addAttribute(
                "productList",
                inventoryService.findAll()
        );

        return "inventory";
    }

    @PostMapping("/update")
    public String updateStock(
            @RequestParam long productId,
            @RequestParam int stock,
            RedirectAttributes redirectAttributes) {

        try {
            inventoryService.updateStock(productId, stock);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "在庫数を更新しました。"
            );

        } catch (IllegalArgumentException | IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );
        }

        return "redirect:/inventory";
    }
}
package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Member;
import com.example.demo.model.Product;
import com.example.demo.service.InventoryService;

import lombok.RequiredArgsConstructor;

/**
 * 管理者向けの商品・在庫管理画面を担当します。
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    /** 商品管理一覧を表示します。 */
    @GetMapping
    public String showInventory(
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        try {
            model.addAttribute(
                    "productList",
                    inventoryService.findAll());
            model.addAttribute(
                    "imageOptions",
                    inventoryService.getImageOptions());

        } catch (IllegalStateException e) {
            model.addAttribute("productList", List.of());
            model.addAttribute(
                    "imageOptions",
                    inventoryService.getImageOptions());
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "inventory";
    }

    /** 新しい商品を登録します。 */
    @PostMapping("/register")
    public String register(
            @RequestParam String productName,
            @RequestParam BigDecimal price,
            @RequestParam int stock,
            @RequestParam int categoryId,
            @RequestParam(required = false) String description,
            @RequestParam String imageUrl,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategoryId(categoryId);
        product.setDescription(description);
        product.setImageUrl(imageUrl);

        try {
            inventoryService.register(product);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "商品を登録しました。");

        } catch (IllegalArgumentException |
                 IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
        }

        return "redirect:/inventory";
    }

    /** 在庫数だけを一覧画面から更新します。 */
    @PostMapping("/update")
    public String updateStock(
            @RequestParam long productId,
            @RequestParam int stock,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        try {
            inventoryService.updateStock(productId, stock);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "在庫数を更新しました。");

        } catch (IllegalArgumentException |
                 IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
        }

        return "redirect:/inventory";
    }

    /** 商品編集画面を表示します。 */
    @GetMapping("/edit")
    public String showEdit(
            @RequestParam long productId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        try {
            model.addAttribute(
                    "product",
                    inventoryService.findById(productId));
            model.addAttribute(
                    "imageOptions",
                    inventoryService.getImageOptions());
            return "inventoryEdit";

        } catch (IllegalArgumentException |
                 IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
            return "redirect:/inventory";
        }
    }

    /** 商品の基本情報を更新します。 */
    @PostMapping("/edit")
    public String updateProduct(
            @RequestParam long productId,
            @RequestParam String productName,
            @RequestParam BigDecimal price,
            @RequestParam int stock,
            @RequestParam int categoryId,
            @RequestParam(required = false) String description,
            @RequestParam String imageUrl,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategoryId(categoryId);
        product.setDescription(description);
        product.setImageUrl(imageUrl);

        try {
            inventoryService.updateProduct(product);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "商品情報を更新しました。");
            return "redirect:/inventory";

        } catch (IllegalArgumentException |
                 IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
            return "redirect:/inventory/edit?productId=" + productId;
        }
    }

    /** 商品を論理削除して取扱停止にします。 */
    @PostMapping("/stop")
    public String stopProduct(
            @RequestParam long productId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        try {
            inventoryService.stopProduct(productId);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "商品の取扱いを停止しました。");

        } catch (IllegalArgumentException |
                 IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
        }

        return "redirect:/inventory";
    }

    /** 取扱停止中の商品を再開します。 */
    @PostMapping("/resume")
    public String resumeProduct(
            @RequestParam long productId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        try {
            inventoryService.resumeProduct(productId);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "商品の取扱いを再開しました。");

        } catch (IllegalArgumentException |
                 IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
        }

        return "redirect:/inventory";
    }

    /**
     * ログインユーザーが管理者か確認します。
     * セッション名が異なる場合は「loginUser」を実際の名前へ変更してください。
     */
    private boolean isAdmin(HttpSession session) {

        Object sessionUser = session.getAttribute("loginUser");

        if (!(sessionUser instanceof Member member)) {
            return false;
        }

        return "ADMIN".equalsIgnoreCase(member.getRole());
    }
}
package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.InventoryService;

import lombok.RequiredArgsConstructor;

/**
 * 商品在庫画面を担当するControllerです。
 *
 * 主な役割
 * ・商品一覧を画面に表示する
 * ・入力された在庫数へ更新する
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * 商品在庫一覧を表示します。
     *
     * URL：
     * GET /inventory
     *
     * @param model JSPへ渡すデータ
     * @return inventory.jsp
     */
    @GetMapping
    public String showInventory(Model model) {

        try {
            /*
             * 商品一覧を取得してJSPへ渡します。
             *
             * JSPではproductListという名前で使用します。
             */
            model.addAttribute(
                    "productList",
                    inventoryService.findAll());

        } catch (IllegalStateException e) {

            /*
             * DB接続エラーなどが発生した場合は、
             * 空の商品一覧をJSPへ渡します。
             */
            model.addAttribute(
                    "productList",
                    List.of());

            model.addAttribute(
                    "errorMessage",
                    e.getMessage());
        }

        return "inventory";
    }

    /**
     * 商品の在庫数を更新します。
     *
     * URL：
     * POST /inventory/update
     *
     * @param productId 商品ID
     * @param stock 変更後の在庫数
     * @param redirectAttributes リダイレクト後にメッセージを渡すためのもの
     * @return 在庫一覧画面へリダイレクト
     */
    @PostMapping("/update")
    public String updateStock(
            @RequestParam("productId") long productId,
            @RequestParam("stock") int stock,
            RedirectAttributes redirectAttributes) {

        try {
            inventoryService.updateStock(
                    productId,
                    stock);

            /*
             * 更新成功後に表示するメッセージです。
             */
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "在庫数を更新しました。");

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            /*
             * 入力値エラーやDBエラーが発生した場合に、
             * エラーメッセージを表示します。
             */
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
        }

        /*
         * 更新後にGET /inventoryへ移動します。
         *
         * ブラウザを再読み込みしたときに、
         * 同じ更新処理が再実行されることを防ぎます。
         */
        return "redirect:/inventory";
    }
}
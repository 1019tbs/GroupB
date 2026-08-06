document.addEventListener("DOMContentLoaded", () => {
    const reservationSection =
            document.getElementById("reservation");
    const productSelect =
            document.getElementById("menuId");
    const quantityInput =
            document.getElementById("reservationQuantity");
    const pickupDateInput =
            document.getElementById("pickupDate");
    const selectedName =
            document.getElementById("reservationSelectionName");
    const selectedMeta =
            document.getElementById("reservationSelectionMeta");

    if (!reservationSection
            || !productSelect
            || !quantityInput
            || !selectedName
            || !selectedMeta) {
        return;
    }

    const formatLocalDate = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1)
                .padStart(2, "0");
        const day = String(date.getDate())
                .padStart(2, "0");

        return `${year}-${month}-${day}`;
    };

    if (pickupDateInput && !pickupDateInput.min) {
        pickupDateInput.min = formatLocalDate(new Date());
    }

    const updateSelection = () => {
        const option =
                productSelect.options[productSelect.selectedIndex];

        if (!option || !option.value) {
            selectedName.textContent = "商品を選択してください";
            selectedMeta.textContent =
                    "商品カードの「店頭受取を予約」からも選べます";
            quantityInput.removeAttribute("max");
            return;
        }

        const stock = Number(option.dataset.stock || 0);
        const price = Number(option.dataset.price || 0);
        const quantity = Math.max(
                1,
                Number(quantityInput.value || 1));
        const safeQuantity = stock > 0
                ? Math.min(quantity, stock)
                : quantity;

        quantityInput.value = String(safeQuantity);
        quantityInput.max = String(stock);

        selectedName.textContent =
                option.dataset.productName || option.textContent.trim();
        selectedMeta.textContent =
                `数量 ${safeQuantity}点 ／ 予定金額 ￥${(
                        price * safeQuantity
                ).toLocaleString("ja-JP")} ／ 在庫 ${stock}点`;
    };

    productSelect.addEventListener(
            "change",
            updateSelection);
    quantityInput.addEventListener(
            "input",
            updateSelection);

    document.querySelectorAll("[data-reserve-product]")
            .forEach((button) => {
                button.addEventListener("click", () => {
                    productSelect.value =
                            button.dataset.reserveProduct || "";
                    updateSelection();
                    reservationSection.scrollIntoView({
                        behavior: "smooth",
                        block: "start"
                    });

                    window.setTimeout(() => {
                        quantityInput.focus({
                            preventScroll: true
                        });
                    }, 450);
                });
            });

    updateSelection();
});

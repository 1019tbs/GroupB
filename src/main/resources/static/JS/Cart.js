document.addEventListener("DOMContentLoaded", function () {

    const cartForms = document.querySelectorAll(
        'form.cardBottom[action$="/cart/add"]'
    );

    cartForms.forEach(function (form) {

        form.addEventListener("submit", function () {

            // カート追加ボタンを押した時点の位置を保存
            sessionStorage.setItem(
                "menuScrollPosition",
                String(window.scrollY)
            );
        });
    });
});

window.addEventListener("load", function () {

    const savedPosition =
        sessionStorage.getItem("menuScrollPosition");

    if (savedPosition === null) {
        return;
    }

    // 画像の読み込み完了後、保存した位置へ戻す
    window.scrollTo({
        top: Number(savedPosition),
        left: 0,
        behavior: "auto"
    });

    // 次回通常表示時には復元しないよう削除
    sessionStorage.removeItem("menuScrollPosition");
});
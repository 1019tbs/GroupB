-- -- productsテーブルへ論理削除用のactive列を追加します。
-- -- TRUE  : 取扱中
-- -- FALSE : 取扱停止

BEGIN;

ALTER TABLE products
ADD COLUMN IF NOT EXISTS active BOOLEAN NOT NULL DEFAULT TRUE;

-- 既存の商品名と大文字・小文字だけが違う重複も防止します。
-- 同じ商品を再販売するときは、新規登録ではなく「取扱再開」を使用します。
CREATE UNIQUE INDEX IF NOT EXISTS uq_products_product_name_lower
ON products (LOWER(product_name));

COMMIT;



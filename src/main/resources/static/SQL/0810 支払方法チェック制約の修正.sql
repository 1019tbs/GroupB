-- =====================================================
-- 店頭受取予約：支払方法チェック制約の修正
--
-- 原因：
-- shopping_orders.payment_method のCHECK制約に
-- 「pay_at_store」が含まれていなかったため、
-- 店頭受取予約の注文確定時にDB登録エラーが発生していた。
--
-- 対応：
-- 既存の支払方法を維持したまま、
-- 「pay_at_store」を許可するよう制約を再作成する。
-- =====================================================

-- 手順１
-- 現在の支払方法チェック制約を削除する。
-- 既存の制約では「pay_at_store（店頭支払い）」が許可されておらず、
-- 店頭受取予約の注文登録時にエラーが発生するため、一度制約を削除する。

ALTER TABLE shopping_orders
DROP CONSTRAINT ck_shopping_orders_payment;


-- 手順２
-- 支払方法のチェック制約を再設定する。
-- 既存の支払方法
-- 「credit」「bank」「cash_on_delivery」「convenience_store」
-- に加えて、店頭受取用の「pay_at_store」を許可する。

-- ALTER TABLE shopping_orders
-- ADD CONSTRAINT ck_shopping_orders_payment
-- CHECK (
--     payment_method IN (
--         'credit',
--         'bank',
--         'cash_on_delivery',
--         'convenience_store',
--         'pay_at_store'
--     )
-- );
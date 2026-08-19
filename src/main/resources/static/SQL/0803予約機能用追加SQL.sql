BEGIN;

-- 商品ごとに店頭受取・通販の可否を管理する。
ALTER TABLE products
    ADD COLUMN IF NOT EXISTS pickup_available BOOLEAN;

ALTER TABLE products
    ADD COLUMN IF NOT EXISTS delivery_available BOOLEAN;

UPDATE products
SET pickup_available = COALESCE(pickup_available, TRUE),
    delivery_available = COALESCE(delivery_available, TRUE);

ALTER TABLE products
    ALTER COLUMN pickup_available SET DEFAULT TRUE,
    ALTER COLUMN pickup_available SET NOT NULL,
    ALTER COLUMN delivery_available SET DEFAULT TRUE,
    ALTER COLUMN delivery_available SET NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'chk_products_fulfillment_available'
          AND conrelid = 'products'::regclass
    ) THEN
        ALTER TABLE products
            ADD CONSTRAINT chk_products_fulfillment_available
            CHECK (pickup_available OR delivery_available);
    END IF;
END $$;

-- 1つのカートには1種類の受取方法だけを入れる。
ALTER TABLE carts
    ADD COLUMN IF NOT EXISTS fulfillment_method VARCHAR(20);

UPDATE carts c
SET fulfillment_method = 'DELIVERY'
WHERE fulfillment_method IS NULL
  AND EXISTS (
      SELECT 1
      FROM cart_items ci
      WHERE ci.cart_id = c.cart_id
  );

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'chk_carts_fulfillment_method'
          AND conrelid = 'carts'::regclass
    ) THEN
        ALTER TABLE carts
            ADD CONSTRAINT chk_carts_fulfillment_method
            CHECK (
                fulfillment_method IS NULL
                OR fulfillment_method IN ('DELIVERY', 'PICKUP')
            );
    END IF;
END $$;

-- 通販と店頭受取を同じ注文・注文明細・注文履歴で管理する。
ALTER TABLE shopping_orders
    ADD COLUMN IF NOT EXISTS fulfillment_method VARCHAR(20),
    ADD COLUMN IF NOT EXISTS pickup_date DATE,
    ADD COLUMN IF NOT EXISTS pickup_time TIME;

UPDATE shopping_orders
SET fulfillment_method = COALESCE(
        fulfillment_method,
        'DELIVERY'
    );

ALTER TABLE shopping_orders
    ALTER COLUMN fulfillment_method SET DEFAULT 'DELIVERY',
    ALTER COLUMN fulfillment_method SET NOT NULL,
    ALTER COLUMN postal_code DROP NOT NULL,
    ALTER COLUMN address DROP NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'chk_shopping_orders_fulfillment'
          AND conrelid = 'shopping_orders'::regclass
    ) THEN
        ALTER TABLE shopping_orders
            ADD CONSTRAINT chk_shopping_orders_fulfillment
            CHECK (
                (
                    fulfillment_method = 'DELIVERY'
                    AND pickup_date IS NULL
                    AND pickup_time IS NULL
                )
                OR
                (
                    fulfillment_method = 'PICKUP'
                    AND pickup_date IS NOT NULL
                    AND pickup_time IS NOT NULL
                )
            );
    END IF;
END $$;

CREATE INDEX IF NOT EXISTS idx_shopping_orders_fulfillment
    ON shopping_orders (member_id, fulfillment_method, created_at DESC);

COMMIT;
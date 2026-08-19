BEGIN;

INSERT INTO products (
    product_name,
    price,
    stock,
    category_id,
    description,
    image_url
) VALUES
    -- CAKES
    ('ラズベリー・オペラ',
     880, 20, 1, NULL,
     '/images/cake_opera.png'),

    ('ハニカム・ムースケーキ',
     690, 20, 1, NULL,
     '/images/cake_honey.png'),

    ('ブラックフォレスト・ドーム',
     880, 20, 1, NULL,
     '/images/cake_bkforest.png'),

    ('バッテンバーグケーキ',
     780, 20, 1, NULL,
     '/images/cake_batten.png'),

    ('クロカンブッシュケーキ',
     780, 20, 1, NULL,
     '/images/cake_shuu.png'),

    ('アールグレイムースケーキ',
     680, 20, 1, NULL,
     '/images/cake_moose.png'),

    -- BAKES
    ('クラシック焼き菓子セット',
     1650,
     20,
     2,
     'ショートブレッド ×4 ／ フロランタン×4、アイシングクッキー ×2 ／ バイカラクッキー ×2',
     '/images/bakes_classic.png'),

    ('フルーティータイムセット',
     1580,
     20,
     2,
     'ラズベリーフィナンシェ ×2 ／ レモンのパンケーキ ×2、クランベリーとオレンジのフルーツケーキ ×2、アプリコットとピスタチオのサブレサンド ×2',
     '/images/bakes_fruity.png'),

    ('Honey & ナッツタイムセット',
     1480,
     20,
     2,
     'ハニー＆タイムのショートブレッド ×2、アーモンドとくるみのフロランタン ×2、はちみつロープ ×2 ／ メープルナッツフィナンシェ ×2',
     '/images/bakes_nuts.png'),

    -- PASTRIES
    ('ほうれん草とベーコンのキッシュ',
     520, 20, 3, NULL,
     '/images/pastries_quiche.png'),

    ('きのことチェダーの三角ハンドパイ',
     590, 20, 3, NULL,
     '/images/pastries_knkpie.png'),

    ('トマトとリコッタのガレット',
     680, 20, 3, NULL,
     '/images/pastries_galette.png'),

    ('ハニーアップルパイ',
     690, 20, 3, NULL,
     '/images/pastries_apple.png'),

    ('ベリーピスタチオミルフィーユ',
     780, 20, 3, NULL,
     '/images/pastries_millef.png'),

    ('ヘーゼルナッツチョコパイ',
     680, 20, 3, NULL,
     '/images/pastries_choco.png');

COMMIT;
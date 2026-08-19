CREATE TABLE postal_code_search(
jis_code         VARCHAR(10), -- 全国地方公共団体コード
old_postal_code  VARCHAR(10), -- 旧郵便番号（5桁）
postal_code      VARCHAR(10), -- 7桁郵便番号（超重要）
prefecture_kana  VARCHAR(300), -- 都道府県（カナ）
city_kana        VARCHAR(300), -- 市区町村（カナ）
town_kana        VARCHAR(1000), -- 町域（カナ）
prefecture       VARCHAR(300), -- 都道府県（超重要）
city             VARCHAR(300), -- 市区町村（超重要）
town             VARCHAR(1000), -- 町域（超重要）
is_multiple_postal_code VARCHAR(30), -- 複数郵便番号フラグ
has_koaza        VARCHAR(10), -- 小字番地フラグ
has_chome        VARCHAR(10), -- 丁目保有フラグ
is_multiple_town VARCHAR(10), -- 複数町域フラグ
update_status    VARCHAR(10), -- 更新表示
update_reason    VARCHAR(10) -- 変更理由
);

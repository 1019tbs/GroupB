INSERT INTO members (
    member_id,
    password,
    member_name,
    postal_code,
    address,
    phone_number,
    birth_date,
    email,
    payment_method,
    role
) VALUES (
    'test01',
    '1234',
    'テスト太郎',
    '100-0001',
    '東京都千代田区千代田1-1',
    '090-1234-5678',
    '2000-01-01',
    'test01@example.com',
    'CREDIT_CARD',
    'USER'
);

INSERT INTO members (
    member_id,
    password,
    member_name,
    postal_code,
    address,
    phone_number,
    birth_date,
    email,
    payment_method,
    role
) VALUES (
    'admin01',
    'admin1234',
    '管理者',
    '530-0001',
    '大阪府大阪市北区梅田1-1',
    '090-9999-9999',
    '1990-01-01',
    'admin@example.com',
    'CREDIT_CARD',
    'ADMIN'
);
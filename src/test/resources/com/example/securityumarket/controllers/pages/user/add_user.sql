INSERT INTO users (name, email, password, phone, refresh_token, city, created, last_update, active)
VALUES ('Temp',
        'temp@tempmail.com',
        '$2a$05$6syFHkuhxeD0RhXDe.kpROVsnMvn1ZnDKhoA5XCsSdYhEsZQGicrm',
        '+380980000001',
        'refresh_token_value',
        1,
        TO_TIMESTAMP('2023-08-26 20:31:23', 'YYYY-MM-DD HH24:MI:SS'),
        NOW(),
        true);

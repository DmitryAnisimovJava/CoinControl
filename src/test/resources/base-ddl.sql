INSERT INTO coin_repository."user" (id, email, password, avatar_path, name, role)
VALUES ('e0ee31f6-6c9b-4987-8fba-246b059b3bee', 'user1@email.ru',
        '$2a$12$PSzUV/J/v3v2UZB3U0gY0ePKfqA7xB8GldXzLI9MveUt6zVApQe/y', '/default', 'imyaCheloveka',
        'ADMIN'), --123 password
       ('fbd42fb4-f0f0-498a-90d0-92e221bc094d', 'user2@google.com',
        '$2a$12$.yMJv83BgebQgPSQK.r8TefpT1LLHhaNjQbILwMLmS//8eAxJRpQq', '/my-avatar', 'nechelovecheskoeImya',
        'USER'); --1234 password

INSERT INTO coin_repository.wallet (id, user_id, name, balance)
VALUES ('74dd2764-c0cc-4195-a906-b8edd7804c03', 'e0ee31f6-6c9b-4987-8fba-246b059b3bee', 'Main wallet', 1000),
       ('55646589-a2c7-456e-bed4-7c8b3d45e22c', 'e0ee31f6-6c9b-4987-8fba-246b059b3bee', 'Second wallet', 50),
       ('cc375d74-b34a-4386-865b-a7214919e26a', 'fbd42fb4-f0f0-498a-90d0-92e221bc094d', 'My money', 500);

INSERT INTO coin_repository.transaction (id, wallet_id, amount, date, transaction_type, category, notes)
VALUES ('4c3e97a5-4bc1-4c58-bf83-7b4229d69603', '74dd2764-c0cc-4195-a906-b8edd7804c03', 50, NOW(), 'expense', 'HOUSE',
        'notes'),
       ('2378202d-4c06-4688-916c-0e09ae845d09', '74dd2764-c0cc-4195-a906-b8edd7804c03', 100, NOW() - INTERVAL '1 month',
        'income', 'SALARY', NULL),
       ('09331bd1-c8cb-4bf5-931b-fdb4cfe21d24', '74dd2764-c0cc-4195-a906-b8edd7804c03', 45, NOW() - INTERVAL '1 day',
        'expense', 'CAFE', 'lost money'),
       ('7aab80c5-dee3-45b3-8539-1c4515c18132', 'cc375d74-b34a-4386-865b-a7214919e26a', 150, NOW() - INTERVAL '7 day',
        'expense', 'GIFT', NULL),
       ('f96b4954-3880-4591-bf9f-8db390b0703b', 'cc375d74-b34a-4386-865b-a7214919e26a', 150, NOW() - INTERVAL '7 day',
        'expense', 'SPORT', NULL),
       ('aa3fa2e3-0b19-43f0-9540-39cb49c76698', '55646589-a2c7-456e-bed4-7c8b3d45e22c', 200, NOW() - INTERVAL '200 day',
        'income', 'BUSINESS', NULL);



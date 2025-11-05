-- Insert user
INSERT INTO user_info (id, username, password, profile_image, full_name, created_at) VALUES
('1477d6d1-ba33-411b-b82b-1a00e3e9ff66', 'annora_c', '$2a$10$Ti5tsCjuHabAXbXSR6xTuuKjQ3HqcH0G9fXdFHe.pyFISkcfJDiHO', 'https://randomuser.me/api/portraits/women/12.jpg', 'Annora Crossfeld', CURRENT_TIMESTAMP),
('bb1a9186-39c1-42f0-9fa2-eff1b8c8d7eb', 'bob_b',   '$2a$10$Ti5tsCjuHabAXbXSR6xTuuKjQ3HqcH0G9fXdFHe.pyFISkcfJDiHO', 'https://randomuser.me/api/portraits/men/45.jpg',   'Bob Balotelli',   CURRENT_TIMESTAMP),
('4b89b598-ed57-4554-a37a-d77c06397bf4', 'chris_c', '$2a$10$Ti5tsCjuHabAXbXSR6xTuuKjQ3HqcH0G9fXdFHe.pyFISkcfJDiHO', 'https://randomuser.me/api/portraits/men/68.jpg', 'Chris Radford', CURRENT_TIMESTAMP),
('2587b5bd-0945-4be0-a687-9e1d93dc6425', 'dave_n',  '$2a$10$Ti5tsCjuHabAXbXSR6xTuuKjQ3HqcH0G9fXdFHe.pyFISkcfJDiHO', 'https://randomuser.me/api/portraits/men/3.jpg',    'Dave Newberry',  CURRENT_TIMESTAMP),
('b69f12d9-798e-4af3-b29f-7c57ffc5c311', 'eve_p',   '$2a$10$Ti5tsCjuHabAXbXSR6xTuuKjQ3HqcH0G9fXdFHe.pyFISkcfJDiHO', 'https://randomuser.me/api/portraits/women/99.jpg', 'Eve Penwright',   CURRENT_TIMESTAMP);

INSERT INTO product (id, name, price, image, description, sku, created_at) VALUES
('8f14e45f-ea3b-4c3a-9f8a-1a2b3c4d5e6f', 'Wireless Headphones', 79.99, 'https://images.pexels.com/photos/3394650/pexels-photo-3394650.jpeg', 'Over-ear wireless headphones with noise cancellation.', 'WH-1000X', CURRENT_TIMESTAMP),
('9b1deb4d-9a5c-4f3b-8c2d-7e6f5a4b3c2d', 'Smartwatch Pro', 199.50, 'https://cdn.pixabay.com/photo/2015/06/25/17/22/smart-watch-821559_1280.jpg', 'Fitness smartwatch with heart-rate monitoring and GPS.', 'SW-PRO-42', CURRENT_TIMESTAMP),
('1c2b3a4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', '4K Action Camera', 149.00, 'https://images.pexels.com/photos/4268525/pexels-photo-4268525.jpeg', 'Compact 4K action camera with waterproof housing.', 'AC-4K-01', CURRENT_TIMESTAMP),
('2d3c4b5a-6f7e-8d9c-0b1a-2c3d4e5f6a7b', 'Bluetooth Speaker', 49.95, 'https://images.pexels.com/photos/4917455/pexels-photo-4917455.jpeg', 'Portable Bluetooth speaker with 12h battery life.', 'BS-PORT-12', CURRENT_TIMESTAMP),
('3e4d5c6b-7a8f-9b0c-1d2e-3f4a5b6c7d8e', 'Gaming Mouse', 39.99, 'https://images.pexels.com/photos/6956290/pexels-photo-6956290.jpeg', 'Ergonomic gaming mouse with programmable buttons.', 'GM-ERGO-07', CURRENT_TIMESTAMP);

-- Insert discounts (reference product ids above)
INSERT INTO discount (id, product_id, description, percentage, start_date, end_date) VALUES
('a3ae2b29-c76f-44f8-8810-73b700836901', '8f14e45f-ea3b-4c3a-9f8a-1a2b3c4d5e6f', 'Holiday sale - wireless headphones', 10.00, '2025-11-25 00:00:00', '2025-12-31 23:59:59'),
('20d7c3c1-23f6-4344-8b13-a2660089ab17', '9b1deb4d-9a5c-4f3b-8c2d-7e6f5a4b3c2d', 'Black Friday - smartwatch discount', 25.50, '2025-11-28 00:00:00', '2025-11-30 23:59:59'),
('ffa5b73c-fd59-4b5c-ac1e-573886e1e7ba', '9b1deb4d-9a5c-4f3b-8c2d-7e6f5a4b3c2d', 'A Test Discount', 5.00, '2025-11-01 00:00:00', '2025-11-30 23:59:59'),
('eac24228-db89-49c7-8a88-5d9db06996e0', '1c2b3a4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', 'New year promo - action camera', 5.00, '2025-12-26 00:00:00', '2026-01-10 23:59:59');

INSERT INTO comment (id, product_id, user_id, content, created_at) VALUES
('d4f1a7c2-0b3e-4a6f-9c8d-11e2f3a4b5c7', '8f14e45f-ea3b-4c3a-9f8a-1a2b3c4d5e6f', '1477d6d1-ba33-411b-b82b-1a00e3e9ff66', 'Great sound quality and long battery life.', CURRENT_TIMESTAMP),
('e7b2c9a1-1c4f-4d7a-8b9e-22f3a4b5c6d8', '8f14e45f-ea3b-4c3a-9f8a-1a2b3c4d5e6f', 'bb1a9186-39c1-42f0-9fa2-eff1b8c8d7eb', 'Comfortable fit and excellent noise cancellation.', CURRENT_TIMESTAMP),
('a3c5d6e7-2d5f-4b8a-9f0e-33a4b5c6d7e9', '8f14e45f-ea3b-4c3a-9f8a-1a2b3c4d5e6f', '4b89b598-ed57-4554-a37a-d77c06397bf4', 'Worth the price, clear mids and highs.', CURRENT_TIMESTAMP),
('b5e6f7a8-3e6f-4c9b-0a1d-44b5c6d7e8f0', '8f14e45f-ea3b-4c3a-9f8a-1a2b3c4d5e6f', '2587b5bd-0945-4be0-a687-9e1d93dc6425', 'Pairing was quick and stable.', CURRENT_TIMESTAMP),
('c6f7a8b9-4f7a-4d0c-1b2e-55c6d7e8f901', '8f14e45f-ea3b-4c3a-9f8a-1a2b3c4d5e6f', 'b69f12d9-798e-4af3-b29f-7c57ffc5c311', 'Bass is punchy, great for workouts.', CURRENT_TIMESTAMP),

('f1a2b3c4-5d6e-7f80-9a1b-2c3d4e5f6a11', '9b1deb4d-9a5c-4f3b-8c2d-7e6f5a4b3c2d', '1477d6d1-ba33-411b-b82b-1a00e3e9ff66', 'Stylish design and accurate health tracking.', CURRENT_TIMESTAMP),
('f2b3c4d5-6e7f-801a-2b3c-4d5e6f7a8222', '9b1deb4d-9a5c-4f3b-8c2d-7e6f5a4b3c2d', 'bb1a9186-39c1-42f0-9fa2-eff1b8c8d7eb', 'Battery lasts several days with normal use.', CURRENT_TIMESTAMP),
('f3c4d5e6-7f80-91a2-3b4c-5d6e7f8a9333', '9b1deb4d-9a5c-4f3b-8c2d-7e6f5a4b3c2d', '4b89b598-ed57-4554-a37a-d77c06397bf4', 'GPS is accurate during runs.', CURRENT_TIMESTAMP),
('f4d5e6f7-8091-a2b3-4c5d-6e7f8a9b0444', '9b1deb4d-9a5c-4f3b-8c2d-7e6f5a4b3c2d', '2587b5bd-0945-4be0-a687-9e1d93dc6425', 'Screen is bright and responsive.', CURRENT_TIMESTAMP),
('f5e6f7a8-90a1-b2c3-4d5e-6f7a8b9c1555', '9b1deb4d-9a5c-4f3b-8c2d-7e6f5a4b3c2d', 'b69f12d9-798e-4af3-b29f-7c57ffc5c311', 'Good value for the features offered.', CURRENT_TIMESTAMP),

('a11b22c3-d34e-56f7-890a-1b2c3d4e5f66', '1c2b3a4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', '1477d6d1-ba33-411b-b82b-1a00e3e9ff66', 'Image stabilization works well for action shots.', CURRENT_TIMESTAMP),
('b22c33d4-e45f-67a8-901b-2c3d4e5f6a77', '1c2b3a4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', 'bb1a9186-39c1-42f0-9fa2-eff1b8c8d7eb', 'Compact and easy to mount on helmets.', CURRENT_TIMESTAMP),
('c33d44e5-f56a-78b9-012c-3d4e5f6a7b88', '1c2b3a4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', '4b89b598-ed57-4554-a37a-d77c06397bf4', 'Video quality is sharp in daylight.', CURRENT_TIMESTAMP),
('d44e55f6-a67b-89c0-123d-4e5f6a7b8c99', '1c2b3a4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', '2587b5bd-0945-4be0-a687-9e1d93dc6425', 'Battery could be better for long trips.', CURRENT_TIMESTAMP),
('e55f66a7-b78c-90d1-234e-5f6a7b8c9daa', '1c2b3a4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', 'b69f12d9-798e-4af3-b29f-7c57ffc5c311', 'Easy to use and great stabilization.', CURRENT_TIMESTAMP),

('a66b77c8-d89e-01f2-3456-6a7b8c9d0abb', '2d3c4b5a-6f7e-8d9c-0b1a-2c3d4e5f6a7b', '1477d6d1-ba33-411b-b82b-1a00e3e9ff66', 'Compact speaker with impressive bass for its size.', CURRENT_TIMESTAMP),
('b77c88d9-e90f-12a3-4567-7b8c9d0a1bcc', '2d3c4b5a-6f7e-8d9c-0b1a-2c3d4e5f6a7b', 'bb1a9186-39c1-42f0-9fa2-eff1b8c8d7eb', 'Battery life is solid for outdoor use.', CURRENT_TIMESTAMP),
('c88d99ea-f01a-23b4-5678-8c9d0a1b2cdd', '2d3c4b5a-6f7e-8d9c-0b1a-2c3d4e5f6a7b', '4b89b598-ed57-4554-a37a-d77c06397bf4', 'Loud enough and clear at high volumes.', CURRENT_TIMESTAMP),
('d99eaa0b-012b-34c5-6789-9d0a1b2c3dee', '2d3c4b5a-6f7e-8d9c-0b1a-2c3d4e5f6a7b', '2587b5bd-0945-4be0-a687-9e1d93dc6425', 'Easy to carry and connects fast.', CURRENT_TIMESTAMP),
('eaa0bb1c-123c-45d6-7890-ad1b2c3d4eff', '2d3c4b5a-6f7e-8d9c-0b1a-2c3d4e5f6a7b', 'b69f12d9-798e-4af3-b29f-7c57ffc5c311', 'Great soundstage for such a small speaker.', CURRENT_TIMESTAMP),

('ab11cd22-34ef-56a7-890b-bc1d2e3f4a00', '3e4d5c6b-7a8f-9b0c-1d2e-3f4a5b6c7d8e', '1477d6d1-ba33-411b-b82b-1a00e3e9ff66', 'Comfortable and responsive for long gaming sessions.', CURRENT_TIMESTAMP),
('bc22de33-45f0-67b8-901c-cd2e3f4a5b11', '3e4d5c6b-7a8f-9b0c-1d2e-3f4a5b6c7d8e', 'bb1a9186-39c1-42f0-9fa2-eff1b8c8d7eb', 'High DPI and programmable buttons are handy.', CURRENT_TIMESTAMP),
('cd33ef44-56a1-78c9-012d-de3f4a5b6c22', '3e4d5c6b-7a8f-9b0c-1d2e-3f4a5b6c7d8e', '4b89b598-ed57-4554-a37a-d77c06397bf4', 'Responsive sensor and comfortable grip.', CURRENT_TIMESTAMP),
('de44f055-67b2-89d0-123e-ef4a5b6c7d33', '3e4d5c6b-7a8f-9b0c-1d2e-3f4a5b6c7d8e', '2587b5bd-0945-4be0-a687-9e1d93dc6425', 'Buttons feel durable and crisp.', CURRENT_TIMESTAMP),
('ef55g166-78c3-90e1-234f-f05b6c7d8e44', '3e4d5c6b-7a8f-9b0c-1d2e-3f4a5b6c7d8e', 'b69f12d9-798e-4af3-b29f-7c57ffc5c311', 'Great for both work and gaming.', CURRENT_TIMESTAMP);

-- Insert Categories
INSERT INTO category (id, name, description) VALUES
                                                 (1, 'Electronics', 'Electronic devices and accessories'),
                                                 (2, 'Clothing', 'Men and women apparel and fashion'),
                                                 (3, 'Home & Kitchen', 'Home appliances and kitchen essentials'),
                                                 (4, 'Sports & Outdoors', 'Sports equipment and outdoor gear'),
                                                 (5, 'Books', 'Fiction, non-fiction and educational books');

-- Insert Products
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES
                                                                                        (1,  'iPhone 15 Pro',              'Apple smartphone with A17 Pro chip',              50,   999.99,  1),
                                                                                        (2,  'Samsung 4K TV 55 inch',      '55 inch 4K Ultra HD Smart LED TV',                30,  1299.99,  1),
                                                                                        (3,  'Sony Headphones WH-1000XM5', 'Noise cancelling wireless headphones',            75,   349.99,  1),
                                                                                        (4,  'Laptop Dell XPS 15',         '15.6 inch FHD display, Intel i7, 16GB RAM',       20,  1799.99,  1),
                                                                                        (5,  'Men''s Running Shoes',       'Lightweight breathable running shoes',            120,    89.99,  2),
                                                                                        (6,  'Women''s Yoga Pants',        'High waist stretchable yoga pants',               200,    49.99,  2),
                                                                                        (7,  'Winter Jacket',              'Waterproof insulated winter jacket',               80,   129.99,  2),
                                                                                        (8,  'Air Fryer 5.8 Qt',           'Digital touchscreen air fryer with 8 presets',    45,    99.99,  3),
                                                                                        (9,  'Instant Pot Duo 7-in-1',     'Electric pressure cooker multi-use',              60,    89.99,  3),
                                                                                        (10, 'Non-stick Cookware Set',     '10-piece aluminum non-stick cookware set',         35,   149.99,  3),
                                                                                        (11, 'Yoga Mat',                   'Eco-friendly non-slip exercise yoga mat',         150,    29.99,  4),
                                                                                        (12, 'Dumbbells Set 20kg',         'Adjustable rubber coated dumbbell set',            40,   119.99,  4),
                                                                                        (13, 'Mountain Bike 26 inch',      '21-speed shimano gear mountain bicycle',           15,   499.99,  4),
                                                                                        (14, 'Atomic Habits',              'Build good habits by James Clear',                300,    16.99,  5),
                                                                                        (15, 'The Pragmatic Programmer',   'Software craftsmanship guide for developers',     100,    49.99,  5);

-- Update sequences
SELECT setval('category_seq', 100);
SELECT setval('product_seq', 100);
-- init_data.sql
-- Insert an admin user with a pre-hashed password (using BCrypt)
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$12$YzCClXbTfLyEx3RUMRU.Wu5K4fWTFzp4WcdhD06v97aLppP53G21a', 'ADMIN')
ON CONFLICT (username) DO NOTHING;

-- (Optional) Insert a few sample products
INSERT INTO products (name, sku, category, quantity, cost_price, selling_price)
VALUES 
    ('Milk', 'MILK001', 'Dairy', 50, 0.80, 1.20),
    ('Bread', 'BRED001', 'Bakery', 30, 0.50, 0.90)
ON CONFLICT (sku) DO NOTHING;

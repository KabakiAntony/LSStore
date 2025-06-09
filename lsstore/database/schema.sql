-- schema.sql
-- Create USERS table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Create PRODUCTS table (Inventory)
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sku VARCHAR(30) UNIQUE NOT NULL,
    category VARCHAR(50),
    quantity INTEGER DEFAULT 0,
    cost_price NUMERIC(10,2),
    selling_price NUMERIC(10,2),
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create SALES table
CREATE TABLE IF NOT EXISTS sales (
    id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES products(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL,
    total_price NUMERIC(10,2),
    sold_by INTEGER REFERENCES users(id),
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- (Optional) Create SYSTEM_LOG table for debugging and record-keeping
CREATE TABLE IF NOT EXISTS system_log (
    id SERIAL PRIMARY KEY,
    message TEXT,
    log_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

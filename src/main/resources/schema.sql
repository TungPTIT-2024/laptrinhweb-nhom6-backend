-- Basic schema matching entities: product, discount, comment, user_info
-- Executed on application startup when using Spring Boot with schema.sql present.

CREATE TABLE IF NOT EXISTS product (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(19,2),
    image VARCHAR(500),
    description VARCHAR(4000),
    sku VARCHAR(255),
    created_at TIMESTAMP,
    modified_at TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS discount (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    product_id VARCHAR(36),
    description VARCHAR(255),
    percentage DECIMAL(5,2),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    created_at TIMESTAMP,
    modified_at TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS comment (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    product_id VARCHAR(36),
    user_id VARCHAR(36),
    content VARCHAR(2000),
    created_at TIMESTAMP,
    modified_at TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS user_info (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    username VARCHAR(200),
    password VARCHAR(2000),
    full_name VARCHAR(255),
    profile_image VARCHAR(500),
    created_at TIMESTAMP,
    modified_at TIMESTAMP
    );
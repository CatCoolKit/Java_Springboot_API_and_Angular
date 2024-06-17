create database shopapp;
USE shopapp;

--user
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);
alter table users add column role_id INT;

--role
CREATE TABLE roles (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);
alter table users add foreign key (role_id) references roles(id);

--token
CREATE TABLE tokens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--support login with Facebook and Google
CREATE TABLE social_accounts (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `provider` VARCHAR(20) NOT NULL COMMENT 'Ten nha social network',
    `provider_id` VARCHAR(20) NOT NULL,
    `email` VARCHAR(150) NOT NULL COMMENT 'Email tai khoan',
    `name` VARCHAR(100) NOT NULL COMMENT 'Ten nguoi dung',
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--category product
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
);

--product
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(350) COMMENT 'Tên sản phẩm',
    price FLOAT NOT NULL CHECK (price >= 0),
    thumbnail VARCHAR(300) DEFAULT '',
    description LONGTEXT DEFAULT '',
    created_at DATETIME,
    updated_at DATETIME,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

--product image
CREATE TABLE product_images(
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_product_images_product_id 
        FOREIGN KEY (product_id) 
        REFERENCES products (id) ON DELETE CASCADE,
    image_url VARCHAR(300)
);

--order
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    fullname VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50),
    total_price FLOAT CHECK (total_price >= 0)
);

ALTER TABLE orders ADD COLUMN shipping_method VARCHAR (100);
ALTER TABLE orders ADD COLUMN shipping_address VARCHAR (200);
ALTER TABLE orders ADD COLUMN shipping_date DATE;
ALTER TABLE orders ADD COLUMN tracking_number VARCHAR (100);
ALTER TABLE orders ADD COLUMN payment_method VARCHAR (100);
ALTER TABLE orders ADD COLUMN active TINYINT(1);

MODIFY COLUMN status ENUM('pending', 'processing', "shipped", "delivered", "cancelled")
COMMENT 'Trạng thái đơn hàng'; 


--order detail
CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    price FLOAT CHECK (price >= 0),
    quantity INT CHECK (quantity > 0),
    total_price FLOAT CHECK (total_price >= 0),
    color VARCHAR(20) DEFAULT ''
);
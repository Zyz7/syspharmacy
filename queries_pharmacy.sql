
CREATE TABLE IF NOT EXISTS `employees` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(100) NOT NULL DEFAULT '',
  `username` VARCHAR(60) NOT NULL DEFAULT '',
  `address` VARCHAR(100) NOT NULL DEFAULT '',
  `telephone` VARCHAR(20) NOT NULL DEFAULT '',
  `email` VARCHAR(100) NOT NULL DEFAULT '',
  `password` VARCHAR(20) NOT NULL,
  `role` VARCHAR(60) NOT NULL DEFAULT '',
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `suppliers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL DEFAULT '',
  `description` VARCHAR(255) NOT NULL DEFAULT '',
  `telephone` VARCHAR(20) NOT NULL DEFAULT '',
  `address` VARCHAR(100) NOT NULL DEFAULT '',
  `email` VARCHAR(100) NOT NULL DEFAULT '',
  `city` VARCHAR(50) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL DEFAULT '',
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR2(15) NOT NULL DEFAULT '',
  `name` VARCHAR(100) NOT NULL DEFAULT '',
  `description` VARCHAR(255) NOT NULL DEFAULT '',
  `unit_price` DOUBLE NOT NULL,
  `quantity` INT NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(100) NOT NULL DEFAULT '',
  `address` VARCHAR(100) NOT NULL DEFAULT '',
  `telephone` VARCHAR(20) NOT NULL DEFAULT '',
  `email` VARCHAR(100) NOT NULL DEFAULT '',
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `purchases` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total` DOUBLE NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `updated` TIMESTAMP NOT NULL,
  `supplier_id` INT NOT NULL,
  `employee_id` INT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `purchase_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `price` DOUBLE NOT NULL,
  `amount` INT NOT NULL,
  `subtotal` DOUBLE NOT NULL,
  `purchase_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `sales` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP NOT NULL,
  `total` DOUBLE NOT NULL,
  `customer_id` INT NOT NULL,
  `employee_id` INT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `sales_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `subtotal` DOUBLE NOT NULL,
  `product_id` INT NOT NULL,
  `sale_id` INT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



ALTER TABLE `products` ADD INDEX `product_category_idx` (`category_id` ASC) VISIBLE;

ALTER TABLE `products` ADD CONSTRAINT `product_category`
FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `purchases` ADD INDEX `purchase_supplier_idx` (`supplier_id` ASC) VISIBLE,
ADD INDEX `purchase_employee_idx` (`employee_id` ASC) VISIBLE;

ALTER TABLE `purchases`
ADD CONSTRAINT `purchase_supplier`
FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `purchases`
ADD CONSTRAINT `purchase_employee`
FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `purchase_details` ADD INDEX `purchase_details_idx` (`purchase_id` ASC) VISIBLE,
ADD INDEX `product_details_idx` (`product_id` ASC) VISIBLE;

ALTER TABLE `purchase_details` ADD CONSTRAINT `purchase_details`
FOREIGN KEY (`purchase_id`) REFERENCES `purchases` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `purchase_details` ADD CONSTRAINT `product_details`
FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `sales` ADD INDEX `sales_customer_idx` (`customer_id` ASC) VISIBLE,
ADD INDEX `sales_employee_idx` (`employee_id` ASC) VISIBLE;

ALTER TABLE `sales`
ADD CONSTRAINT `sales_customer`
FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `sales`
ADD CONSTRAINT `sales_employee`
FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `sales_details` ADD INDEX `product_details_idx` (`product_id` ASC) VISIBLE,
ADD INDEX `sales_details_idx` (`sale_id` ASC) VISIBLE;

ALTER TABLE `sales_details` ADD CONSTRAINT `products_details`
FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `sales_details` ADD CONSTRAINT `sale_details`
FOREIGN KEY (`sale_id`) REFERENCES `sales` (`id`)
ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE employees ADD CONSTRAINT employees_un_unique UNIQUE (username);
ALTER TABLE employees ADD CONSTRAINT employees_e_unique UNIQUE (email);
ALTER TABLE suppliers ADD CONSTRAINT suppliers_unique UNIQUE (email);
ALTER TABLE categories ADD CONSTRAINT categories_unique UNIQUE (name);
ALTER TABLE products ADD CONSTRAINT products_unique UNIQUE (code);
ALTER TABLE customers ADD CONSTRAINT customers_unique UNIQUE (email);

ALTER TABLE employees DROP INDEX employees_unique;



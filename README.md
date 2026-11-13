# SysPharmacy 💊

<img width="1198" height="675" alt="Screenshot_20251201_145127" src="https://github.com/user-attachments/assets/49ce07f0-77ad-41c9-9bec-647a88528571" />

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Swing](https://img.shields.io/badge/GUI-Swing-blue)
![MySQL](https://img.shields.io/badge/Database-MySQL-informational)
![License](https://img.shields.io/badge/License-Included-green)

SysPharmacy is a desktop pharmacy management system built with **Java** and **Swing**, backed by a relational **database**. It provides user registration and login, role-based access control, and full management of the day-to-day operations of a pharmacy: employees, customers, products, suppliers, categories, purchases and sales.

---

## ✨ Features

- 🔐 **User registration and login** — secure access to the application.
- 👥 **Role-based access** — two user roles:
  - **Administrator**: full access to every module.
  - **Staff**: restricted access (e.g. cannot register purchases).
- 🧑‍💼 **Employees** — manage pharmacy staff records.
- 🙋 **Customers** — manage customer information (name, address, phone, email).
- 💊 **Products** — manage the product catalog, linked to categories and suppliers.
- 🏷️ **Categories** — organize products by category.
- 🚚 **Suppliers** — manage supplier information for restocking.
- 🛒 **Purchases** — record incoming stock from suppliers.
- 💰 **Sales** — record sales with full transaction details (quantity, price, subtotal, total).
- 📋 **Listing & printing** — view all purchases and sales, and print any transaction record.
- 🖥️ **Intuitive dashboard** — Swing-based graphical interface.

## 🛠️ Tech Stack

| Layer     | Technology                  |
|-----------|-----------------------------|
| Language  | Java                        |
| GUI       | Swing                       |
| Database  | MySQL / MariaDB (InnoDB)    |
| Build     | Apache Ant (NetBeans project) |

## 📂 Project Structure

The codebase follows an **MVC-inspired architecture**:

```
syspharmacy/
├── src/
│   ├── controllers/   # Application logic connecting views and models
│   ├── data/          # Database access layer
│   ├── models/        # Data models (entities)
│   ├── views/         # Swing GUI windows and panels
│   ├── images/        # UI icons and images
│   └── main/          # Application entry point
├── nbproject/         # NetBeans project configuration
├── build/             # Compiled build output
├── conector/          # JDBC driver / connector library
├── syspharmacy.sql    # MySQL database dump (schema + sample data)
├── syspharmacy.db     # Database file
├── build.xml          # Ant build script
└── manifest.mf        # JAR manifest
```

## 🗄️ Database Schema

The database includes (among others) the following tables:

- `employees` — staff records
- `users` — application accounts (administrator / staff roles)
- `customers` — customer data (`full_name`, `address`, `telephone`, `email`)
- `suppliers` — supplier data
- `categories` — product categories
- `products` — product catalog
- `purchases` — purchase transactions
- `sales` — sale transactions (`date`, `total`, `customer_id`, `employee_id`)
- `sales_details` — line items per sale (`quantity`, `price`, `subtotal`, `product_id`, `sale_id`)

Tables are linked with foreign keys (e.g. sales → customers, sales → employees, sales_details → products).

## 🚀 Getting Started

### Prerequisites

- **JDK 8 or later** installed
- **MySQL** or **MariaDB** server running
- (Recommended) **Apache NetBeans** with Ant support, or the Ant CLI

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/Zyz7/syspharmacy.git
   cd syspharmacy
   ```

2. **Import the database**

   ```bash
   mysql -u root -p < syspharmacy.sql
   ```

3. **Configure the connection**

   Update the database credentials (host, user, password) in the data/connection layer of the project (see `src/data/`).

4. **Build and run**

   Using NetBeans: open the project and press **Run** (F6).

   Or using Ant from the command line:

   ```bash
   ant clean build
   java -jar dist/syspharmacy.jar
   ```

## 🧭 Usage

1. Launch the application and **register or log in** with your user account.
2. Depending on your role (**administrator** or **staff**), navigate the dashboard to manage employees, customers, products, suppliers, categories, purchases and sales.
3. Record purchases and sales; list all movements and **print** any transaction record.

> ⚠️ Staff accounts have certain options blocked, such as registering new purchases. Use an administrator account for full access.

## 📄 License

This project is distributed under the license included in the [LICENSE](LICENSE) file.

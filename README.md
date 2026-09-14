# CampusBites Smart Canteen (Java + MySQL + HTML)

Mumbai University mini-project folder. The browser UI is HTML/CSS/JS. The backend is Java (Spring Boot + JDBC). All menu, users, and orders are stored in **MySQL**.

## Project folder

```
CampusBites-Java/
  pom.xml
  database/schema.sql
  src/main/java/com/campusbites/
  src/main/resources/application.properties
  src/main/resources/static/
```

## What you need installed

1. **JDK 17** or newer
2. **Maven** (or IntelliJ IDEA / Eclipse with Maven)
3. **MySQL** (XAMPP or MySQL Workbench is fine)

## Step 1: Start MySQL

Open XAMPP and start **MySQL**, or start MySQL Server.

Set local MySQL credentials with environment variables or your local
`application.properties` (do not commit passwords):

```
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
```

The app creates database `campusbites` and tables automatically on first run. You can also run `database/schema.sql` in MySQL Workbench.

## Step 2: Run the project

```
cd CampusBites-Java
mvn spring-boot:run
```

Then open **http://localhost:8080**.

## Demo logins

| Role | Phone | Password |
|---|---|---|
| Student | 8888888888 | student123 |
| Admin | 9999999999 | admin123 |

Students and faculty can use **Register**. New student wallets start at ₹2000
and faculty wallets at ₹3000; these defaults can be changed with
`campusbites.wallet.initial.student` and `campusbites.wallet.initial.faculty`.

## Discounts and coupons

- `SAVE10` = 10%
- `WELCOME5` = 5%
- `CAMPUS15` = 15%
- Automatic subtotal tiers are 5% from ₹600 to below ₹1200, 10% from ₹1200
  through ₹1600, and 15% above ₹1600.
- If a coupon is entered, only the higher saving is applied (coupon and
  automatic discount are not stacked). Ties use the coupon label and amount.

## Modules

1. Student/faculty register and login (`users` table)
2. Menu from MySQL (`menu_items`)
3. Cart and coupons
4. Checkout (offline, demo online, or Campus Wallet)
5. Orders saved in MySQL (`orders`, `order_items`) with prices taken from the database
6. My Orders and tracking
7. Admin dashboard, status update, menu add/edit/delete

## Important viva points

- This is not a real bank payment. Card/UPI screens are a demo UI.
- Passwords are stored using SHA-256.
- Admin page requires an ADMIN role session.
- Order totals are recalculated in Java using MySQL menu prices.
- Campus Wallet deduction uses a conditional SQL update inside the same
  transaction as order creation, so insufficient balances are rejected and a
  successful order returns the remaining balance.

# Smart Canteen

Spring Boot Smart-Canteen ordering application for students and faculty.

## Business rules

The server calculates every subtotal from submitted line items and applies these
tiers before payment:

| Subtotal | Tier discount |
| --- | ---: |
| Below ₹600 | 0% |
| ₹600–₹1,199.99 | 5% |
| ₹1,200–₹1,600 inclusive | 10% |
| Above ₹1,600 | 15% |

Thus ₹1,600 receives 10%, while ₹1,600.01 receives 15%. The optional `SAVE50`
coupon adds a fixed ₹50 discount for subtotals of at least ₹500; discounts are
capped at the subtotal so the payable amount never becomes negative.

## Wallets

`STUDENT` accounts start with ₹2,000 and `FACULTY` accounts with ₹3,000. A
wallet checkout locks the account row, checks the balance, saves the order, and
debits the wallet in one transaction. Insufficient funds return `409 Conflict`
and no order is created. The response includes the remaining wallet balance.

The API uses HTTP Basic authentication, and authorization derives the account
from the authenticated username. For local demo accounts, set
`DEMO_STUDENT_PASSWORD`, `DEMO_FACULTY_PASSWORD`, and optionally
`DEMO_ADMIN_PASSWORD` before startup. Student/faculty demo users are seeded
only when their passwords are supplied; no public default credentials are
configured. New registrations create student accounts with a ₹2,000 balance.

## Run

Requires Java 8+ and Maven:

```bash
mvn test
mvn spring-boot:run
```

Open `http://localhost:8080/`. The API is `POST /api/orders/checkout` and
`GET /api/accounts/me`, authenticated with the seeded Basic credentials.

## MySQL configuration

Production uses Flyway migrations and validates the JPA schema. Set `DB_URL`,
`DB_USERNAME`, and `DB_PASSWORD` from environment variables or your host's
secret manager. Demo account passwords are controlled by
`DEMO_STUDENT_PASSWORD`, `DEMO_FACULTY_PASSWORD`, and `DEMO_ADMIN_PASSWORD`;
they have no source-controlled defaults. Products are seeded by name and
checkout accepts product IDs, so prices cannot be spoofed by the browser.

Additional authenticated endpoints are `GET /api/products`, `GET /api/orders`,
and `GET /api/wallet/transactions`.

## Accounts, administration, and wallet operations

`POST /api/auth/register` accepts `username`, `displayName`, and a password
(minimum eight characters). Passwords are BCrypt hashes and are never returned.
`POST /api/auth/password-reset/request` and
`POST /api/auth/password-reset/confirm` implement single-use, 30-minute,
cryptographically random reset tokens. Connect the request endpoint to an email
provider in production; it deliberately returns the same response for unknown
users.

Product writes (`POST /api/products`, `PUT /api/products/{id}`, and
`DELETE /api/products/{id}`) require the `ADMIN` role. The delete operation
deactivates rather than removing products referenced by historical orders.
The demo admin password is controlled by `DEMO_ADMIN_PASSWORD`.

Authenticated users can top up with `POST /api/wallet/top-up` (`{"amount": 100}`)
and refund a wallet order with `POST /api/orders/{id}/refund`. Both operations
lock the account row and append an immutable ledger entry. Refunds are
idempotently rejected after the first refund, and admins may refund any wallet
order.

## Production deployment and secrets

Flyway owns schema changes; MySQL production should use a dedicated database
user with least privilege and `spring.jpa.hibernate.ddl-auto=validate`. Set
`DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` from your platform secret manager.
Do not put passwords, keystores, or reset tokens in this repository. For direct
TLS termination by Spring Boot, provide `SERVER_SSL_ENABLED=true`,
`SERVER_SSL_KEY_STORE`, `SERVER_SSL_KEY_STORE_PASSWORD`, and (optionally)
`SERVER_SSL_KEY_ALIAS` as injected secrets. Otherwise terminate HTTPS at a
reverse proxy/load balancer and forward only HTTPS traffic to the application.
Set `DB_REQUIRE_SSL=true` and use a TLS MySQL URL in production.

The sample defaults are for local development only. Build and run with:

```bash
mvn test
mvn package
java -jar target/smart-canteen-1.0.0.jar
```

### Deploying the web app

The repository includes a Dockerfile suitable for container hosts such as
Render. Configure the service's `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`,
`DEMO_ADMIN_PASSWORD`, and any optional demo-user passwords as platform secrets.
Provision a MySQL database separately; this application does not create a
managed database service. Public HTTPS is normally terminated by the hosting
platform's proxy. Do not commit `.env` files, certificates, keystores, or
credentials.

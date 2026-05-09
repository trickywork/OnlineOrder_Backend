# Online Order Backend Configuration

This file records the non-code setup needed to run, test, and redeploy the Online Order backend.

## Runtime Shape

The backend is a Spring Boot service. The low-cost Cloud Run deployment also serves the production React frontend copied into:

```text
src/main/resources/public
```

There are three database modes:

- `demo`: H2 in-memory database, used on Cloud Run for the portfolio demo.
- default local mode: PostgreSQL, usually from `docker-compose.yml`.
- `gcp`: Cloud SQL PostgreSQL, intended for a production-like deployment.

## Database Files

Local Postgres container:

```text
docker-compose.yml
```

Spring config:

```text
src/main/resources/application.yml
```

Schema and seed data:

```text
src/main/resources/database-init.sql
```

Tables created by the init SQL:

- `customers`
- `carts`
- `restaurants`
- `menu_items`
- `order_items`
- `authorities`

Seed data includes restaurants and menu items for the demo UI.

## Local Demo Mode

No Docker database required:

```bash
cd OnlineOrder_Backend
SPRING_PROFILES_ACTIVE=demo ./gradlew bootRun
```

This uses:

```text
jdbc:h2:mem:onlineorder
```

Data resets on restart.

## Local PostgreSQL Mode

Start Postgres:

```bash
cd OnlineOrder_Backend
docker compose up -d db
```

Run the backend:

```bash
DATABASE_URL=localhost \
DATABASE_PORT=5432 \
DATABASE_USERNAME=postgres \
DATABASE_PASSWORD=secret \
INIT_DB=always \
./gradlew bootRun
```

The Docker volume is:

```text
onlineorder-pg-local
```

To fully reset local data:

```bash
docker compose down -v
docker compose up -d db
```

## Cloud SQL Mode

Use this only when you want persistence on Google Cloud:

```env
SPRING_PROFILES_ACTIVE=gcp
CLOUD_SQL_CONNECTION_NAME=project:region:instance
DATABASE_NAME=onlineorder
DATABASE_USERNAME=<user>
DATABASE_PASSWORD=<secret>
INIT_DB=never
```

For first-time schema creation, set `INIT_DB=always` once, verify the tables, then switch back to `INIT_DB=never` to avoid wiping data.

## API Testing

Postman collection:

```text
Online Order - Portfolio API Smoke Tests
```

Variables:

```text
baseUrl=http://localhost:8080
```

Test order:

1. `POST /signup`
2. `GET /restaurants/menu`
3. `GET /restaurant/{restaurantId}/menu`
4. `POST /cart`
5. `GET /cart`
6. `POST /cart/checkout`

## Cloud Resources

Google Cloud project:

```text
caramel-vim-441513-e1
```

Region:

```text
us-central1
```

Cloud Run service:

```text
onlineorder
```

Cloud Run URL:

```text
https://onlineorder-gb7rmueyna-uc.a.run.app
```

Custom domain:

```text
onlineorder.junliu.dev
```

Cloud Build trigger:

```text
onlineorder-main-deploy
```

Current Cloud Run env vars:

```text
SPRING_PROFILES_ACTIVE=demo
INIT_DB=always
```

## Cost Notes

- Current demo mode has no Cloud SQL cost.
- Cloud Run is configured for `min-instances=0`.
- Switching to Cloud SQL adds a monthly database cost even when the app is idle.

# Online Order Backend

Spring Boot backend for the Online Order portfolio project. The deployed version also serves the production React build from the frontend repo, so the Cloud Run URL demonstrates the complete ordering flow in one low-cost service.

## Live Demo

- Portfolio URL: `https://onlineorder.junliu.dev`
- Cloud Run service: `onlineorder`
- Cloud Run URL: `https://onlineorder-888561484971.us-central1.run.app`
- Google Cloud project: `caramel-vim-441513-e1`
- Region: `us-central1`

## Tech Stack

- Java 17
- Spring Boot 3.3
- Spring Web
- Spring Security
- Spring Data JDBC
- PostgreSQL driver
- H2 database for demo mode
- Gradle wrapper
- Docker, Google Cloud Build, Google Cloud Run
- Postman collection for API testing

## Project Structure

```text
OnlineOrder_Backend/
  src/main/java/OnlineOrder/
    controller/
    db/
    entity/
    model/
    service/
  src/main/resources/
    application.yml
    database-init.sql
    public/
  docs/
    configuration.md
  postman/
    OnlineOrder_Backend.postman_collection.json
  Dockerfile
  cloudbuild.yaml
  docker-compose.yml
```

## Features

- Customer signup and login through Spring Security.
- Restaurant list and menu lookup.
- Add menu items to cart.
- View cart with item totals.
- Checkout and clear cart.
- Demo H2 profile for cheap Cloud Run hosting.
- PostgreSQL profile for local or full production-style development.

## Local Development: Demo Mode

Demo mode uses H2 in memory and does not require a database container.

```bash
SPRING_PROFILES_ACTIVE=demo ./gradlew bootRun
```

Expected local URL:

```text
http://localhost:8080
```

Expected result:

- The app starts without PostgreSQL.
- Seed data from `database-init.sql` is available.
- The React frontend can show restaurants, menus, cart operations, and checkout.
- Data resets when the process restarts.

## Local Development: PostgreSQL

Start local PostgreSQL:

```bash
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

Expected result:

- PostgreSQL stores the seeded restaurant/menu/customer/cart data.
- Restarting the backend does not erase database state unless the schema is reinitialized.

## Environment Variables

Common variables:

```env
PORT=8080
SPRING_PROFILES_ACTIVE=demo
INIT_DB=always
```

PostgreSQL variables:

```env
DATABASE_URL=localhost
DATABASE_PORT=5432
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=secret
```

Google Cloud SQL variables for a full deployment:

```env
SPRING_PROFILES_ACTIVE=gcp
CLOUD_SQL_CONNECTION_NAME=project:region:instance
DATABASE_NAME=onlineorder
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=secret
INIT_DB=never
```

The current portfolio deployment uses `demo` mode to avoid Cloud SQL monthly cost.

## API Endpoints

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/signup` | Register a customer. |
| `GET` | `/restaurants/menu` | List restaurants. |
| `GET` | `/restaurant/{restaurantId}/menu` | List menu items for one restaurant. |
| `GET` | `/cart` | Read the current cart. |
| `POST` | `/cart` | Add a menu item to cart. |
| `POST` | `/cart/checkout` | Checkout and clear the cart. |
| `GET` | `/hello` | Smoke test endpoint. |

## Postman

Import:

```text
postman/OnlineOrder_Backend.postman_collection.json
```

Suggested variables:

```text
baseUrl=http://localhost:8080
```

For Cloud Run:

```text
baseUrl=https://onlineorder-888561484971.us-central1.run.app
```

## Frontend Pairing

Development frontend repo:

```text
/Users/junliu/git_repo/OnlineOrder_Frontend
https://github.com/trickywork/OnlineOrder_Frontend
```

The deployed backend repo serves a production frontend build from:

```text
src/main/resources/public
```

That keeps Cloud Run cost low because the demo only needs one running service.

## Tests

```bash
SPRING_PROFILES_ACTIVE=demo ./gradlew test --no-daemon
```

## Cloud Deployment

Manual deployment:

```bash
gcloud builds submit \
  --config cloudbuild.yaml \
  --project caramel-vim-441513-e1
```

Cloud Run cost controls:

- `min-instances=0`
- `max-instances=1`
- H2 demo database instead of Cloud SQL
- frontend served from the same container

## Expected Portfolio Behavior

A visitor should be able to open the URL, sign in or register, browse restaurants, add food to the cart, open the cart drawer, and complete checkout. In demo mode the data is temporary and can reset when the Cloud Run instance restarts.

## Additional Notes

Detailed runtime and database setup notes are in:

```text
docs/configuration.md
```

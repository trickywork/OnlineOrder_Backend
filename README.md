# Online Order Backend

Spring Boot backend for the Online Order project. The Cloud Run deployment also serves the built React frontend from `src/main/resources/public`, so the portfolio URL can demonstrate login, menu browsing, cart operations, and checkout in one low-cost service.

## Live Service

- Cloud Run service: `onlineorder`
- Current URL: `https://onlineorder-gb7rmueyna-uc.a.run.app`
- Custom domain mapping: `onlineorder.junliu.dev` is configured and waiting for Google-managed certificate provisioning.

## Features

- Customer signup.
- Restaurant list and menu lookup.
- Add menu items to cart.
- Read cart and checkout.
- Demo profile with in-memory H2 for cheap Cloud Run deployment.
- Local PostgreSQL profile through `docker-compose.yml`.

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring JDBC/JPA repositories
- PostgreSQL for local/full deployment
- H2 for Cloud Run demo mode
- Gradle wrapper

## Local Run With Demo H2

```bash
SPRING_PROFILES_ACTIVE=demo ./gradlew bootRun
```

Then open:

```text
http://localhost:8080
```

## Local Run With PostgreSQL

Start Postgres:

```bash
docker compose up -d db
```

Run backend:

```bash
DATABASE_URL=localhost \
DATABASE_PORT=5432 \
DATABASE_USERNAME=postgres \
DATABASE_PASSWORD=secret \
INIT_DB=always \
./gradlew bootRun
```

## Tests

```bash
SPRING_PROFILES_ACTIVE=demo ./gradlew test --no-daemon
```

## API Endpoints

| Method | Path | Description |
| --- | --- | --- |
| POST | `/signup` | Register a customer. |
| GET | `/restaurants/menu` | List restaurants. |
| GET | `/restaurant/{restaurantId}/menu` | List menu items for one restaurant. |
| GET | `/cart` | Read current cart. |
| POST | `/cart` | Add a menu item to cart. |
| POST | `/cart/checkout` | Checkout and clear cart. |
| GET | `/hello` | Simple backend smoke endpoint. |

## API Testing

Import `postman/OnlineOrder_Backend.postman_collection.json` into Postman. Change `baseUrl` to the Cloud Run URL when testing the deployed version.

## Configuration Notes

Non-code setup is documented in `docs/configuration.md`, including H2 demo mode, local PostgreSQL with Docker Compose, Cloud SQL variables, schema/init SQL, and Cloud Run settings.

## Cloud Run Deployment

The repo includes `Dockerfile` and `cloudbuild.yaml`.

```bash
gcloud builds submit --config cloudbuild.yaml --project caramel-vim-441513-e1
```

The Cloud Build trigger `onlineorder-main-deploy` deploys from GitHub pushes to `main`.

Cost controls:

- Cloud Run `min-instances=0`.
- Cloud Run `max-instances=1`.
- Demo deployment uses H2 in memory, so there is no Cloud SQL monthly cost.

## Demo vs Full Deployment

Demo mode is cheap and good for portfolio clicks. Data resets when the Cloud Run instance restarts.

Full mode should use PostgreSQL or Cloud SQL and the `gcp` Spring profile:

```text
SPRING_PROFILES_ACTIVE=gcp
CLOUD_SQL_CONNECTION_NAME=<project:region:instance>
DATABASE_NAME=onlineorder
DATABASE_USERNAME=<user>
DATABASE_PASSWORD=<secret>
INIT_DB=never
```

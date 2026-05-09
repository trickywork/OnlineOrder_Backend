# Online Order API

Base URLs:

- Local H2 demo: `http://localhost:8080`
- Cloud Run: `https://onlineorder-gb7rmueyna-uc.a.run.app`

## Signup

`POST /signup`

```json
{
  "email": "demo@example.com",
  "password": "password",
  "first_name": "Demo",
  "last_name": "User"
}
```

## Restaurants

`GET /restaurants/menu`

Returns restaurant metadata.

## Menu

`GET /restaurant/{restaurantId}/menu`

Returns menu items for one restaurant.

## Cart

`GET /cart`

Returns current cart.

`POST /cart`

```json
{
  "menu_id": 1
}
```

`POST /cart/checkout`

Checks out and clears the cart.

# Java Web Mobile Shop
This is a Spring MVC-based internet shopping application that supports two user roles: users (shoppers) and administrators. Users can browse products, add items to their cart, and make purchases, while administrators manage product listings and view purchase histories.

# Features
## User Features
- Anonymous User
- Browse categories
- View products
- Add products to the cart
- Define the quantity of products in the cart
- Modify cart contents (remove items or change quantities)

## Authenticated User (Shopper)
- Complete online purchases using cash on delivery or PayPal
- View purchase history with details such as:
  - Items purchased
  - Purchase date
  - Payment method

## Administrator Features
- Manage categories and products (Create, Read, Update, Delete operations)
- View login history (user, time, and IP address)
- View complete purchase history for all users with filters for searching by user and purchase date

# Technologies Used
- Spring MVC
- Thymeleaf
- Spring Security
- Bootstrap (for visual design)
- PayPal API (for payment processing)

# Store Management Tool

## Description

The `Store Management Tool` is a Spring Boot 3 microservice delivering an API meant for product
management. As for now, it uses in-memory H2 database for persisting the product entities and Spring
Security configured with basic authentication

## Interface Design

#### REST Endpoints

**API version:** *api/v1*

| Method | Path                  | RequestBody         | ResponseBody       | Description                                                        |
|--------|-----------------------|---------------------|--------------------|--------------------------------------------------------------------|
| GET    | /products             | -                   | `List<ProductDto>` | Returns a list of all products stored or empty list if no products |
| GET    | /products/{productId} | -                   | `ProductDto`       | Returns the product with corresponding productId                   | 
| POST   | /products             | `NewProductRequest` | -                  | If valid input, creates a product based on NewProductRequest sent  | 
| PATCH  | /products/{productId} | `ProductUpdate`     | `ProductDto`       | If valid input, updates an already existing product                | 
| DELETE | /products/{productId} | -                   | -                  | Deletes the product with productId                                 |

#### JSONs

- `NewProductRequest`

```
{
"name": "Product name", - NotBlank
"description": "Product description",
"price": 25, - PositiveOrZero
"quantity": 25 - Positive
}
``` 

- `ProductUpdate` - nulls are treated as "no update"

```
{
"name": "Product name",
"description": "Product description",
"price": 25,
"quantity": 25
}
``` 

- `ProductDto`

```
{
"productId": "1986bc12-670a-4803-9de0-d9eefec77fa4",
"name": "Product name",
"description": "Product description",
"price": 25,
"quantity": 25
}
``` 

- {productID} as path parameter is has to be correctly formatted UUID string.

## Security
Spring Security has been configured to use `Basic-Auth`, with only 2 users being stored in-memory.

Roles: USER, ADMIN

## References
- Postman collection v2.1 can be found inside the project as `SMT.postman-collection.json`.

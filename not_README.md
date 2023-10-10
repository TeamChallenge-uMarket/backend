# uAuto - backend

## main page 
### endpoints:

### Get types
URL: /api/v1/main/type</br>
Method: GET</br>
Description: Retrieve a list of all types</br>
Example response:
```json
[
  {
    "typeId": 1,
    "type": "легкові"
  },
  {
    "typeId": 2,
    "type": "мототранспорт"
  }
]
```
### Get brands
URL: /api/v1/main/brand</br>
Method: GET</br>
Description: Retrieve a list of all transport brands</br>
Example response:
```json
[
  {
    "brandId": 1,
    "brand": "Toyota"
  },
  {
    "brandId": 2,
    "brand": "Honda"
  }
]
```
### Get models
URL: /api/v1/main/model{brandId}</br>
Method: GET</br>
Description: Retrieve a list of given brand models</br>
Parameters:
- brandId  (required): brand ID</br>

Example response:
```json
[
  {
    "modelId": 1,
    "brand": "Toyota",
    "model": "Corolla"
  },
  {
    "modelId": 2,
    "brand": "Toyota",
    "model": "Camry"
  }
]
```
### Get cities</br>
URL: /api/v1/main/brand</br>
Method: GET</br>
Description: Retrieve a list of cities from DB</br>
Example response:
```json
[
  {
    "cityId": 1,
    "city": "Вінниця"
  },
  {
    "cityId": 2,
    "city": "Луцьк"
  }
]
```
### Get new cars
URL: /api/v1/main/newTransports/{page}/{limit}</br>
Method: GET</br>
Description: Retrieve a list of cars sorted desc by date of creation</br>
Parameters:
- page (required): Page number for pagination (start from 0).
- limit (required): Number of products per page (start from 1).

Example response:
```json
[
  {
    "carId": 22,
    "imgUrlSmall": null,
    "carBrand": "Volkswagen",
    "carModel": "Passat",
    "price": 20000.00,
    "mileage": 80000,
    "city": "Київ",
    "transmission": "Automatic",
    "fuelType": "Petrol",
    "year": 2018,
    "created": "2023-09-26T21:21:32.361650"
  },
  {
    "carId": 23,
    "imgUrlSmall": null,
    "carBrand": "Mazda",
    "carModel": "CX-5",
    "price": 10000.00,
    "mileage": 60000,
    "city": "Львів",
    "transmission": "Manual",
    "fuelType": "Diesel",
    "year": 2017,
    "created": "2023-09-26T21:21:32.361650"
  }
]
```
### Get popular cars
URL: /api/v1/main/popularTransports/{page}/{limit}</br>
Method: GET</br>
Description: Retrieve a list of cars sorted by users viewed</br>
Parameters:
- page (required): Page number for pagination (start from 0).
- limit (required): Number of products per page (start from 1).

Example response:
```json
[
  {
    "carId": 22,
    "imgUrlSmall": null,
    "carBrand": "Volkswagen",
    "carModel": "Passat",
    "price": 20000.00,
    "mileage": 80000,
    "city": "Київ",
    "transmission": "Automatic",
    "fuelType": "Petrol",
    "year": 2018,
    "created": "2023-09-26T21:21:32.361650"
  },
  {
    "carId": 23,
    "imgUrlSmall": null,
    "carBrand": "Mazda",
    "carModel": "CX-5",
    "price": 10000.00,
    "mileage": 60000,
    "city": "Львів",
    "transmission": "Manual",
    "fuelType": "Diesel",
    "year": 2017,
    "created": "2023-09-26T21:21:32.361650"
  }
]
```
### Get recently viewed cars (auth)
URL: /api/v1/main/recentlyViewed/{page}/{limit}</br>
Method: GET</br>
Description: Retrieve a list of cars that auth user visited</br>
Parameters:
- page (required): Page number for pagination (start from 0).
- limit (required): Number of products per page (start from 1).

Example response if user authorized:
```json
[
  {
    "carId": 22,
    "imgUrlSmall": null,
    "carBrand": "Volkswagen",
    "carModel": "Passat",
    "price": 20000.00,
    "mileage": 80000,
    "city": "Київ",
    "transmission": "Automatic",
    "fuelType": "Petrol",
    "year": 2018,
    "created": "2023-09-26T21:21:32.361650"
  },
  {
    "carId": 23,
    "imgUrlSmall": null,
    "carBrand": "Mazda",
    "carModel": "CX-5",
    "price": 10000.00,
    "mileage": 60000,
    "city": "Львів",
    "transmission": "Manual",
    "fuelType": "Diesel",
    "year": 2017,
    "created": "2023-09-26T21:21:32.361650"
  }
]
```
Example of response if user not authorized:
returns empty ResponseEntity.

### Get searched cars by parameters
URL: /api/v1/main/cars/{page}/{limit}</br>
Method: GET</br>
Description: Retrieve a list of searched cars</br>

Parameters:
- page (required): Page number for pagination (start from 0).
- limit (required): Number of products per page (start from 1).

Request body parameters:
- orderBy: CREATED, PRICE (only)
- sortBy: ASC, DESC (only)

Request body:
```json
{
    "typeId":"1",
    "brandId":"9",
    "modelId":"43",
    "cityId":"9",
    "orderBy":"CREATED",
    "sortBy": "DESC"
}
```

Example response:
```json
[
  {
    "carId": 22,
    "imgUrlSmall": null,
    "carBrand": "Volkswagen",
    "carModel": "Passat",
    "price": 20000.00,
    "mileage": 80000,
    "city": "Київ",
    "transmission": "Automatic",
    "fuelType": "Petrol",
    "year": 2018,
    "created": "2023-09-26T21:21:32.361650"
  },
  {
    "carId": 23,
    "imgUrlSmall": null,
    "carBrand": "Mazda",
    "carModel": "CX-5",
    "price": 10000.00,
    "mileage": 60000,
    "city": "Львів",
    "transmission": "Manual",
    "fuelType": "Diesel",
    "year": 2017,
    "created": "2023-09-26T21:21:32.361650"
  }
]
```
### Get favorite cars (auth)
URL: /api/v1/main/favoriteCars/{page}/{limit}</br>
Method: GET</br>
Description: Retrieve a list of added to favorite cars by auth user</br>
Parameters:
- page (required): Page number for pagination (start from 0).
- limit (required): Number of products per page (start from 1).

Example response:
```json
[
  {
    "carId": 22,
    "imgUrlSmall": null,
    "carBrand": "Volkswagen",
    "carModel": "Passat",
    "price": 20000.00,
    "mileage": 80000,
    "city": "Київ",
    "transmission": "Automatic",
    "fuelType": "Petrol",
    "year": 2018,
    "created": "2023-09-26T21:21:32.361650"
  },
  {
    "carId": 23,
    "imgUrlSmall": null,
    "carBrand": "Mazda",
    "carModel": "CX-5",
    "price": 10000.00,
    "mileage": 60000,
    "city": "Львів",
    "transmission": "Manual",
    "fuelType": "Diesel",
    "year": 2017,
    "created": "2023-09-26T21:21:32.361650"
  }
]
```
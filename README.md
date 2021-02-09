# person-data-processor
[![Build Status](https://travis-ci.com/agr1277/person-data-processor.svg?branch=main)](https://travis-ci.com/agr1277/person-data-processor)

This project aims to provide utilities as well as a minimal rest API to process 
records containing details about a person/user/customer etc.

The supported input formats are:

```text
LastName | FirstName | Email | FavoriteColor | DateOfBirth
LastName, FirstName, Email, FavoriteColor, DateOfBirth
LastName FirstName Email FavoriteColor DateOfBirth
```

## Usage

#### Launch http API to store records

```shell
$ lein api
Running api at http://127.0.0.1:3000/
```

##### Endpoints

- `POST /records` - Post a record in any input format, supports `text/plain`
- `GET /records/email` - fetch records sorted by email descending, last name 
  ascending
- `GET /records/birthdate` - fetch records sorted by birth date descending
- `GET /records/name` - fetch records sorted by name

Example requests with CURL:

```shell
$ curl -s -X POST http://127.0.0.1:3000/records -H "Content-Type: text/plain" --data "White Joe joe@example.com green 1990-07-19" | jq .
[
  {
    "first_name": "Mark",
    "last_name": "Smith",
    "email": "mark@example.com",
    "favorite_color": "green",
    "date_of_birth": "1992-07-19"
  },
  {
    "first_name": "Joe",
    "last_name": "White",
    "email": "joe@example.com",
    "favorite_color": "green",
    "date_of_birth": "1990-07-19"
  }
]

$ curl -s http://127.0.0.1:3000/records/email | jq .
[
  {
    "first_name": "Mark",
    "last_name": "Smith",
    "email": "mark@example.com",
    "favorite_color": "green",
    "date_of_birth": "1992-07-19"
  },
  {
    "first_name": "Joe",
    "last_name": "White",
    "email": "joe@example.com",
    "favorite_color": "green",
    "date_of_birth": "1990-07-19"
  }
]
```

#### Process records via CLI

```shell
$ lein cli <sort-type> <files>
$ lein cli [default | email-and-last-name | date-of-birth | last-name] 
[files...]
```

For example:
```shell
$ lein cli email-and-last-name demo-pipe.csv demo-comma.csv demo-space.csv 
                                                                                               
| :first_name | :last_name |                  :email | :favorite_color | :date_of_birth |
|-------------+------------+-------------------------+-----------------+----------------|
|     MICHAEL |   ANDERSON |     michael@example.com |          orange |     2007-09-12 |
|     MICHAEL |   ANDERSON |     michael@example.com |             red |     1991-01-04 |
...
```

#### Generate test data

```shell
$ lein generate-data [output-file-prefix] [number of rows to generate]
```

For example:

```shell
$ lein generate-data demo 100
100  rows written to  demo-pipe.csv
100  rows written to  demo-comma.csv
100  rows written to  demo-space.csv
```

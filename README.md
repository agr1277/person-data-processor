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

#### Generate test data

```shell
$ lein generate-data [output-file-prefix] [number of rows to generate]
```

For example:

```shell
$ lein generate-data demo 100                                                                                                                  [8:56:37]
100  rows written to  demo-pipe.csv
100  rows written to  demo-comma.csv
100  rows written to  demo-space.csv
```

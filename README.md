---
title: Console Bank App
category: CLI
layout: 2017/sheet
tags: [Featured]
updated: 2020-08-04
keywords:
  - Console
  - Bank
  - Layered
  - MVC
  - Exceptions
---

Console Bank Application
---------------

## Introduction
We have designed a console-based banking application. This application supports management of: 
  - ui accounts for each customer
  - bank accounts
  - monetary transaction and transaction history

Business logic has been applied to handle illegal operations by the user.

### Data Persistance
Data persistance has been implemented using JDBC and DAO to interface with a MySQL database. The sql model and schema files can be found in the top layer of the project.

Login credentials are serialized and stored in a plain text file `loginCredentials.txt`. This file, if it exists, is loaded in at the beginning of the application, and it written to at the end using file streams. The storage structure is a `Hashmap<Integer,Integer>` where A customer id is matched to the hash of that customer's console password.

### Structure
This project is loosely structured using an MVC layered architecture.

## Usage

### Creating the database locally
To use this application with a MySQL database, begin my creating and connecting to the instance where you would like the schema to live. Then navigate to your local copy of `/Bank/src/com/dollarsbank/localhostmysql/LocalhostMySQLDAOFactory.java` and adjust your connection parameters for your personal connection (I know... hard-coding these details isn't great form. Please see [Expanded relational database support](#expanded-relational-database-support)).

To import the schema using MySQL Workbench, select:
```
Administration > Data Import/Restore
```
Choose `import from self-contained file`, and start the import. The relevant schema will be imported as `bankapp`.

To run the application using the executable `bankapp.jar`:
```bash
cd /Directory/of/bankapp.jar
java --enable-preview -jar bankapp.jar 
```

If you choose to work with the raw project, make sure to add the appropriate JDBC driver to the `classpath`. I am using `mysql-connector-java-8.0.20.jar`.

## Still Under Construction

### Transfers between accounts
Customers should at some point be able to transfer funds from one account to another.
### Expanded relational database support
This includes implementing support for databases other than MySQL in `DAOFactory.java` and also abstracting the connection credentials from `/Bank/src/com/dollarsbank/localhostmysql/LocalhostMySQLDAOFactory.java` to a `.properties` or `.yml` file
### More reliable credential storage
If the application crashes, new login credentials may not be stored in the permanent `loginCredentials.txt` file. At some point, this should not be the case.

### Cross platform `.classpath` support

By default the classpath 
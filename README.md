# Habits Tracker

This is a fullstack app for tracking progress in forming habits.  
Project was created by me during my first apprenticeship in February 2023.

## Node.js

To run fontend you need to have [Node.js](https://nodejs.org) and NPM installed.

## Vue Frontend

All node commands needs to be ran in [frontend subdirectory](/frontend)

### Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```
## Spring Backend

Backend commands can be ran from project directory.

### Compile and Hot-Reload for Development

```sh
./gradlew bootRun
```
 
## Database

To start backend with above command first you have to create and run your local db.  
Then configure [this file](src/main/resources/application.properties) (`url`, `username` & `password`).  
Otherwise backend won't run, compilation errors will occur.  
For db purposes I used XAMPP.

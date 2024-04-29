# Frontend

## Node.js

Żeby odpalić frontend trzeba pobrać [Node.js](https://nodejs.org)
komendy poniżej (przeklejone z pliku README.md projektu vue).

## Project Setup

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
# Backend

## Database

Żeby można było uruchomić backend komendą poniżej, na początku trzeba odpalić bazę danych, 
inaczej wyrzuca błędy kompilacji. Ja do tego używam XAMPPa. Trzeba również dostosować plik
[src/main/resources/application.properties](src/main/resources/application.properties) 
do swojej bazy, tzn. prawdopodobnie zmienić `url`, `username` i `password`.

### Compile and Hot-Reload for Development

```sh
./gradlew bootRun
```
 

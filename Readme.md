# Concorsi Parenti Dipendenti Backend

A scopo statistico mostra quanti parenti fino al n grado di parentela vincono o risultano idonei nei concorsi pubblici dello stesso ente.

Backend Spring Boot  
Frontend Angular

## Compilazione
```
mvn clean package
```

## Docker
### Build
```
docker build -t custom.registry:443/path/dipendentepubico/concorsiparentibackend .
```
### Run
```
docker run -p 8083:8083 custom.registry:443/path/dipendentepubico/concorsiparentibackend
```
### Run Docker-Compose
In alternativa è possibile modificare e lanciare il docker-compose.yaml.
```
docker-compose up -d
```

## OpenAPI
OpenAPI JSON
http://localhost:8083/v3/api-docs/
Swagger UI
http://localhost:8083/swagger-ui.html



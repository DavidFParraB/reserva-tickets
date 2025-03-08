# Implementacion para reservar tickets (reserva-tickets)

## Resumen del problema.

Se requiere dar solución al problema planteado en la reserva de tickets debido a la necesidad de migrar un sistema previamente diseñado, a la arquitectura de la nube, gestionando la escalabilidad para ampliar las fronteras del mercado.

## Criterios en la elección de los componentes.

Debido a las condiciones indicadas como requisito se tuvo en cuenta principalmente las siguientes condiciones:

1. **Seleccion de Base de datos**
    - Se ha optado por una base de datos NoSQL, para guardar los objetos como colecciones, es practico para guardar objetos. Se ha descartado bases de datos relacionales ya que requieren más costos cloud en el despliegue como en el escalamiento.
2. **Capacidad de consultas**
    - Se descarto el uso de bases de datos SQL para evitar la injección de sql.
    - Se ha utilizado el SDK de Firestore para poder atender las consultas con cierto grado de complejidad al filtrar valores como. Fecha del evento, rango de precios y ordenarlo de forma asc/desc.
3. **Creación de reservas**
    - Al realizar la creación de las reservas se consideran el estado de la colección de sillas (seats) , asi como información de la ubicación y del show para realizar la reserva. Esto ocurre en una sola transacción de firestore. Por lo que el recurso se bloquea hasta terminar la actualización lo que evita que se pueda reservar más de una vez la misma ubicación-silla por show.

Esta solución se planteo asi para atender estos tres requisitos principales. 

## Esquema de Seguridad 

### Seguridad Token Bearer

Se ha optado por validar dos tokens de seguridad para los dos tipos de usuario que se tienen en este contexto

1. **Usuario del App**
   - Usuario final que realiza la reserva mediante el App.
   
2. **Usuario Admin**
   - Usuario del sistema que esta encargado de diligenciar la informacion de los eventos.

## Ejemplos de Peticiones:

1. **Solicitud de Reserva**

curl --location 'http://127.0.0.1:8080/reservar' \
--header 'Authorization: Bearer cmVzZXJ2YTp0MWNrZXQwcy0yMDI0MDIzNC1jb21wcmE=' \
--header 'Content-Type: application/json' \
--data '{
    "dni":"10154350954",
    "name": "David",
    "show" :"show_cuatro",
    "location":"platino",
    "seat_number": 5
}'

2. **Consulta de disponibilidad**
curl --location 'https://reserva-tickets-293675118363.us-central1.run.app/show' \
--header 'Authorization: Bearer cmVzZXJ2YS1hZG1pbjp0MWNrZXQwcy0yMDI0MDIzNA=='

3. **Consulta con Filtro**

curl --location 'https://reserva-tickets-293675118363.us-central1.run.app/shows/filter' \
--header 'Authorization: Bearer cmVzZXJ2YS1hZG1pbjp0MWNrZXQwcy0yMDI0MDIzNA==' \
--header 'Content-Type: application/json' \
--data '{
    "init_date": "2025-01-20",
    "end_date": "2025-02-30" ,
    "init_price": 1000,
    "end_price" : 2001,
    "order" : "ASC"
}'

Nota: Se ha dejado por facilidad las fechas de consulta como String con el formato yyyy-MM-dd

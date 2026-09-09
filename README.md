Módulo de Histórico — Wishlist E-commerce

Este módulo es responsable de registrar y consultar el histórico de eventos que ocurren sobre la lista de deseos (wishlist) de los clientes: cada vez que un producto se agrega, elimina o actualiza en la lista de deseos, este servicio guarda un registro (bitácora) de esa acción.

Responsabilidad del módulo

"Tú guardas TODO lo que pasa. Si agregan algo, lo guardas. Si eliminan algo, lo guardas."

Este servicio no controla la lógica de negocio de agregar/eliminar/actualizar productos de la wishlist — eso lo maneja el módulo de Favorite. Este módulo solo recibe la notificación de que algo pasó y la almacena.

Requisitos previos
Java 21
Maven (se usa el wrapper mvnw incluido, no requiere instalación aparte)
Docker Desktop (para levantar PostgreSQL local)
Postman (opcional, para pruebas manuales)
1. Levantar la base de datos (PostgreSQL en Docker)

No es necesario instalar PostgreSQL como programa. Se levanta un contenedor con todo listo:

bash
docker run --name postgres-history -e POSTGRES_PASSWORD=admin123 -e POSTGRES_DB=history_service_db -p 5432:5432 -d postgres

Esto crea:

Usuario: postgres
Contraseña: admin123
Base de datos: history_service_db
Puerto expuesto: 5432

Si el contenedor ya existe y solo necesitas encenderlo de nuevo (por ejemplo, después de reiniciar el PC):

bash
docker start postgres-history

Verificar que está corriendo:

bash
docker ps
2. Configuración de la aplicación

Archivo: src/main/resources/application.properties

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/history_service_db
spring.datasource.username=postgres
spring.datasource.password=admin123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

server.port=8081

El microservicio corre en el puerto 8081 (no el 8080), para poder levantarlo junto a los demás microservicios del equipo sin choques de puerto.

Dependencia necesaria en pom.xml:

xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
3. Script de creación de la tabla (referencia)

Hibernate crea la tabla automáticamente al arrancar la aplicación (ddl-auto=update), pero este es el script equivalente por si se necesita crear manualmente:

sql
CREATE TABLE history_favorite (
    id_history BIGSERIAL PRIMARY KEY,
    id_item_favorite BIGINT,
    action VARCHAR(255),
    date_action TIMESTAMP
);
4. Cómo levantar el proyecto
bash
.\mvnw spring-boot:run

En Linux/Mac:

bash
./mvnw spring-boot:run

Si todo está bien configurado, en la consola debe aparecer:

Hibernate: create table history_favorite (...)
Tomcat started on port 8081 (http)
Started HistoryServiceApplication
5. Modelo de datos
Campo	Tipo	Descripción
idHistory	Long	Identificador único del registro histórico (autogenerado)
idItemFavorite	Long	Referencia al ítem de la wishlist (Favorite) sobre el que ocurrió la acción
action	String	Tipo de acción: AGREGADO, ELIMINADO, ACTUALIZADO
dateAction	LocalDateTime	Fecha y hora exacta en que ocurrió la acción

Este modelo corresponde a la entidad HistoryFavorite del diagrama general del proyecto, con relación 1:N desde Favorite hacia HistoryFavorite.

6. Endpoints disponibles

Base URL: http://localhost:8081/api/historico

Método	Endpoint	Descripción
GET	/api/historico	Lista todo el histórico, ordenado del más reciente al más antiguo
GET	/api/historico/producto/{idItemFavorite}	Lista el histórico de un ítem de favorito específico
POST	/api/historico/test	(Solo para pruebas) Registra un evento manualmente
Ejemplos con Postman

Insertar un evento (solo para pruebas manuales):

POST http://localhost:8081/api/historico/test?idItemFavorite=1&action=AGREGADO

Consultar todo el histórico:

GET http://localhost:8081/api/historico

Consultar histórico de un ítem puntual:

GET http://localhost:8081/api/historico/producto/1
7. Integración con el módulo de Wishlist (Favorite)

El equipo encargado de la lógica de negocio de la wishlist debe inyectar EventoService y llamar al método registrarEvento(...) cada vez que ocurra una acción sobre un ítem de favoritos:

java
// Ejemplo de uso desde otro servicio (FavoriteService)
eventoService.registrarEvento(idItemFavorite, "AGREGADO");
eventoService.registrarEvento(idItemFavorite, "ELIMINADO");
eventoService.registrarEvento(idItemFavorite, "ACTUALIZADO");

Esto guarda automáticamente el registro en la tabla history_favorite, sin que el módulo de Favorite necesite conocer los detalles internos de cómo se almacena el histórico.

8. Notas y pendientes
El endpoint POST /api/historico/test es temporal, se usó únicamente para verificar la conexión con PostgreSQL durante el desarrollo. Se recomienda eliminarlo o protegerlo antes del despliegue final, ya que no forma parte de la lógica de negocio real (el registro debe ocurrir siempre desde el módulo de Favorite, nunca manualmente desde afuera).
Pendiente: agregar pruebas unitarias con JUnit/Mockito para EventoService.
Pendiente: documentar manejo de errores (por ejemplo, qué pasa si idItemFavorite no existe).
Estructura de carpetas del módulo
src/main/java/com/historial/
├── Controller/
│   └── EventoController.java
├── dto/
│   └── EventoDTO.java
├── entity/
│   └── Evento.java
├── repository/
│   └── EventoRepository.java
├── Service/
│   └── EventoService.java
└── HistoryServiceApplication.java
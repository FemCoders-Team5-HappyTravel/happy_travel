# 🌍 Happy Travel - Backend API

Happy Travel es una API REST desarrollada en Java con Spring Boot, que permite gestionar destinos turísticos. Incluye autenticación segura con JWT y documentación con Swagger.

---

## 🚀 Tecnologías

- Java 21
- Spring Boot
- Spring Security (JWT)
- MySQL
- Swagger / OpenAPI
- JPA / Hibernate
- Maven

---

## 📁 Estructura del proyecto

```
src
└── main
    └── java
        └── com.femcoders.happy_travel
            ├── controllers
            ├── dtos
            ├── exceptions
            ├── models
            ├── repositories
            ├── security
            ├── services
            ├── ElectronifyApplication
            └── SqlConfig
    └── resources
        ├── application.properties
        └── data.sql
.env

```

---

## ⚙️ Configuración del entorno

### 🔐 Archivo `.env` (no se sube a GitHub)

Crea un archivo llamado `.env` en la raíz del proyecto con las siguientes variables:

```env
# JWT
JWT_SECRET=yourSuperSecretKey

# Base de datos
DB_URL=jdbc:mysql://localhost:3306/happy_travel
DB_USERNAME=root
DB_PASSWORD=your_password
```

En tu `application.properties` solo debes tener lo esencial, por ejemplo:

```properties
spring.application.name=happy_travel
spring.profiles.active=dev
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

---

## 🔐 Seguridad y autenticación

- Se usa **Spring Security** con **JWT tokens**.
- Los endpoints están protegidos por roles (USER, ADMIN).
- Se puede acceder a rutas como `/login`, `/register` y luego autenticarse con el token en Swagger.

---

## 🧪 Testing

- Se implementa testing con `@WebMvcTest`, `MockMvc`, `@BeforeEach`, principios SOLID y DRY.
- Los endpoints del `DestinationController` tienen su clase de test.

_Ejemplo:_

```java
@BeforeEach
void setUp() {
   // inicialización de mocks y DTOs de prueba
}
```

---

## 📬 Endpoints principales

### 📌 Público

| Método | Ruta             | Descripción                |
|--------|------------------|----------------------------|
| POST   | `/register`      | Registro de usuario        |
| POST   | `/login`         | Login y obtención de token |

### 🔐 Privado (requiere token)

| Método | Ruta                        | Descripción                            |
|--------|-----------------------------|----------------------------------------|
| GET    | `/destinations/user`        | Obtener destinos del usuario logueado |
| POST   | `/destinations/user/{id}`   | Crear destino con imagen              |
| PUT    | `/destinations/{id}`        | Editar destino                        |
| DELETE | `/destinations/{id}`        | Eliminar destino                      |

## 📄 Funcionalidades de la API

### 🔍 Paginación de destinos

El endpoint `GET /destinations/page` permite obtener una **lista paginada de destinos**.  
Utiliza `Spring Data Pageable` y soporta los siguientes parámetros:

```http
GET /destinations/page?page=0&size=5&sort=name,asc
Parámetro	Descripción	Ejemplo
page	Número de página (empezando desde 0)	0
size	Cantidad de elementos por página	5
sort	Campo y orden (asc o desc)	name,asc

💡 Accesible para usuarios con roles ADMIN o USER.

🛡️ Gestión de roles (solo administradores)
El endpoint PUT /users/{id}/roles permite que un administrador actualice los roles de un usuario.


PUT /users/3/roles
Authorization: Bearer {tu_token_de_admin}
Content-Type: application/json

{
  "roles": ["ROLE_USER", "ROLE_MODERATOR"]
}
Requisito	Valor
Token JWT válido	Sí (Bearer ...)
Rol requerido	ROLE_ADMIN
Cuerpo	JSON con lista de roles

🚫 Los usuarios no autorizados recibirán un 403 Forbidden.

🧪 Cómo probar los endpoints
📘 Swagger UI
Abre:

http://localhost:8080/swagger-ui/index.html
Haz clic en Authorize y pega tu token JWT con el prefijo Bearer.

Prueba los endpoints disponibles:

GET /destinations/page

PUT /users/{id}/roles

📫 Postman
Usa el endpoint deseado:


GET http://localhost:8080/destinations/page?page=0&size=5&sort=name,asc
o

PUT http://localhost:8080/users/3/roles
En la pestaña Authorization, selecciona Bearer Token.

En Body, elige raw + JSON y escribe:


{
  "roles": ["ROLE_ADMIN"]
}
---

## 🧪 Cómo ejecutar y probar

1. Clona el repositorio
2. Crea el `.env` como se indicó arriba
3. Asegúrate de tener MySQL corriendo
4. Ejecuta el proyecto desde IntelliJ o terminal:

```bash
./mvnw spring-boot:run
```

5.Abre Swagger en tu navegador.

---


## 💫 Team Members

We proudly collaborate in FemCoders 💜

[![May1704](https://img.shields.io/badge/May1704-cyan?style=for-the-badge&logo=github&logoColor=white)](https://github.com/May1704)
[![VitaFlash](https://img.shields.io/badge/VitaFlash-fuchsia?style=for-the-badge&logo=github&logoColor=white)](https://github.com/vitaFlash)
[![VitaPoperechna](https://img.shields.io/badge/VitaPoperechna-pink?style=for-the-badge&logo=github&logoColor=white)](https://github.com/VitaPoperechna)
[![Alexandracoder](https://img.shields.io/badge/Alexandracoder-purple?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Alexandracoder)
# 🌍 Happy Travel - Backend API

Happy Travel es una API REST desarrollada en Java con Spring Boot, que permite gestionar destinos turísticos. Incluye autenticación segura con JWT, subida de imágenes a Cloudinary, y documentación con Swagger.

---

## 🚀 Tecnologías

- Java 21
- Spring Boot
- Spring Security (JWT)
- MySQL
- Cloudinary (para imágenes)
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
            ├── services
            ├── models
            ├── repositories
            ├── dtos
            ├── config
    └── resources
        ├── application.properties
        ├── static
.env
```

---

## ⚙️ Configuración del entorno

### 🔐 Archivo `.env` (no se sube a GitHub)

Crea un archivo llamado `.env` en la raíz del proyecto con las siguientes variables:

```env
# JWT
JWT_SECRET=yourSuperSecretKey

# Cloudinary
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

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

## 📸 Subida de imágenes

Las imágenes de los destinos se suben a **Cloudinary** usando el `DestinationRequest` con `MultipartFile`.

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

---

## 📖 Documentación con Swagger

Puedes probar la API desde:

```
http://localhost:8080/swagger-ui/index.html
```

- Haz login y copia el token.
- Pulsa "Authorize" e ingresa: `Bearer TU_TOKEN`
- Ya puedes probar endpoints protegidos.

---

## 🧪 Cómo ejecutar y probar

1. Clona el repositorio
2. Crea el `.env` como se indicó arriba
3. Asegúrate de tener MySQL corriendo
4. Ejecuta el proyecto desde IntelliJ o terminal:

```bash
./mvnw spring-boot:run
```

5. Abre Swagger en tu navegador.

---

## 📌 Autor

Desarrollado por **Team5** — Proyecto final **HappyTravel**, con enfoque en backend junior.

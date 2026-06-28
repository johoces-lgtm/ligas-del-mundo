# ⚽ Ecosistema de Microservicios: Ligas del Mundo 🌍

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data-JPA-blue.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![WebClient](https://img.shields.io/badge/WebClient-Reactive-red.svg)

## 📌 Contexto y Visión General

**"Ligas del Mundo"** es un sistema distribuido diseñado para la gestión y sincronización de datos del fútbol mundial. El proyecto implementa una arquitectura basada en **Microservicios Independientes** utilizando el patrón *Database-per-Service*, garantizando un bajo acoplamiento y alta cohesión. 

Cada servicio respeta el patrón arquitectónico **CSR (Controller - Service - Repository)**, aislando estrictamente las responsabilidades, implementando validaciones robustas a través de DTOs y gestionando respuestas HTTP coherentes mediante controladores de excepciones globales (`@ControllerAdvice`).

---

## 👨‍💻 Equipo de Desarrollo

* **Joaquín (Tech Lead / Arquitectura y Sincronización)**
* **Alonso (Dominio Logístico y Geográfico)**
* **Bastián (Dominio de Competencia y Seguridad)**

---

## 🏗️ Topología de Microservicios

El core del sistema se compone principalmente de los siguientes dominios (además de los servicios complementarios de infraestructura):

1. **Microservicio de Clubes:** Gestión del catálogo de equipos.
2. **Microservicio de Partidos:** Administración del fixture, resultados y estados de los encuentros.
3. **Microservicio de Jugadores:** Perfiles, estadísticas y asignación a clubes.
4. **Microservicio Orquestador (`servicio-football-api`):** Módulo central de integración. Actúa como cliente de la API externa (API-Sports) y orquesta la distribución de datos hacia los microservicios internos.
5. **Microservicio Auth y Usuarios:** Emisión de JWT y validación de reglas de negocio para los perfiles.

---

## 🗄️ Lógica de Negocio y Poblado de Datos (Importante)

Debido a la naturaleza distribuida del ecosistema y la integridad referencial de nuestro dominio de fútbol, existe una **dependencia estricta en el orden de los datos**.

* **Dependencia de Sesión (Auth):** Para que un Usuario pueda registrarse e iniciar sesión de forma exitosa en el ecosistema, **su Club favorito debe existir previamente en la base de datos**. Si la tabla de clubes está vacía, el microservicio de Usuarios/Auth rechazará la creación del perfil, impidiendo la emisión del token JWT.
* **Solución de Integración:** Se proveen scripts de poblamiento inicial (`PROYECTO_COMPLETO_BD.sql`) en la carpeta `/bd pobladas/` que deben ser ejecutados justo después de levantar la infraestructura para garantizar el correcto flujo del software.

---

## 🚀 Guía de Despliegue y Ejecución Local

### Prerrequisitos
* **Java 21** y **Maven** instalados en las variables de entorno.
* **Docker Desktop** activo.
* Consola **PowerShell** (Windows) o Terminal (Mac/Linux).
* Cliente SQL (MySQL Workbench, DBeaver) para inyectar los datos.

### Paso 1: Compilación de Binarios
Asegúrese de compilar los ejecutables de todos los módulos sin empaquetar tests que dependan de contextos de red no levantados. Ejecute en la raíz de cada microservicio:
```powershell
mvn clean package -DskipTests

```

### Paso 2: Orquestación con Docker Compose

La infraestructura (MySQL, Eureka, Gateway y los 11 Microservicios) está unificada en una red aislada de Docker.

Abra su PowerShell en la carpeta raíz (`ligas-del-mundo`) y ejecute:

```powershell
docker-compose up -d --build

```

* **Nota Técnica:** Docker Compose levantará el contenedor de base de datos MySQL exponiendo el puerto `3307` hacia su máquina host. Los microservicios de Spring Boot incluyen un mecanismo de *healthcheck* (dependencia estricta) para esperar a que MySQL termine su inicialización antes de arrancar. Espere ~60 segundos.

### Paso 3: Poblado de la Base de Datos

Para satisfacer las reglas de negocio descritas anteriormente, debe inyectar los datos semilla:

1. Abra su cliente SQL preferido (Workbench / DBeaver).
2. Conéctese a la base de datos local usando las credenciales del Docker:
* **Host:** `localhost`
* **Puerto:** `3307`
* **User:** `root` | **Password:** `system`


3. Abra el archivo `PROYECTO_COMPLETO_BD.sql` ubicado en la carpeta `bd pobladas/`.
4. Ejecute el script completo. Esto creará los 12 esquemas y los poblará con Clubes, Países, Ligas y Usuarios base.

### Paso 4: Verificación de Eureka y Gateway

1. **Service Discovery:** Ingrese a `http://localhost:8761` (Eureka Server) y verifique que todos los microservicios figuren en estado `UP`.
2. **Prueba de Flujo:** Con los datos poblados, genere un Token JWT realizando un `POST` al endpoint de Login pasando por el API Gateway (`http://localhost:8080/api/auth/login`).

### Paso 5: Detener la Infraestructura

Para apagar el ecosistema liberando memoria de forma segura, ejecute en PowerShell:

```powershell
docker-compose down

```

*(Los datos poblados en MySQL no se perderán gracias a la persistencia de volúmenes configurada en el docker-compose.yml).*

---

## 📘 Documentación de APIs (Swagger)

Las interfaces de contrato (OpenAPI/Swagger) están configuradas en cada nodo. Una vez levantado el ecosistema, pueden validarse en los siguientes endpoints (ejemplos directos):

* **Seguridad y Usuarios:** `http://localhost:8090/swagger-ui/index.html` (Auth)
* **Jugadores:** `http://localhost:8083/swagger-ui/index.html`
* **Clubes:** `http://localhost:8082/swagger-ui/index.html`
* **Partidos:** `http://localhost:8088/swagger-ui/index.html`
* **Orquestador (Football API):** `http://localhost:8092/swagger-ui/index.html`

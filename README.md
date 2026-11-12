# Defects CafeLab - API Reference

## Overview

- Microservicio: `Defects-CafeLab` (nombre con el que se registra en Eureka)
- Bounded context: `Defects`, separado de `Procedures-CafeLab` sin cambios de lógica ni de rutas.
- Base path real: `/api/v1/defects`
- Formato de respuestas: `application/json`
- Autenticacion: igual que en Procedures, los endpoints son publicos (`permitAll` en `SecurityConfiguration`). El `userId` se envia en el cuerpo o en la ruta.
- Swagger UI disponible en: `/swagger-ui/index.html`
- OpenAPI JSON disponible en: `/v3/api-docs`

## Stack

- Java 17 (el `maven-enforcer-plugin` rechaza otro major)
- Spring Boot 3.2.5, Spring Cloud 2023.0.3 (Eureka client)
- PostgreSQL en Supabase (H2 en memoria para los tests)
- springdoc-openapi 2.5.0

## Variables de entorno

Son las mismas que usa `procedures-cafelab`:

| Variable | Requerida | Descripcion |
| --- | --- | --- |
| `SUPABASE_PROJECT_REF` | Si | Id del proyecto Supabase (el que aparece tras `postgres.` en el dashboard). Se usa en la URL del pooler. |
| `DB_HOST` | No | Host del pooler de Supabase (lo que va despues de `@` en la cadena "Transaction pooler", sin el puerto). Por defecto `aws-1-us-east-2.pooler.supabase.com`. |
| `DB_USERNAME` | Si | Usuario de la base de datos. |
| `DB_PASSWORD` | Si | Password de la base de datos. |
| `EUREKA_URL` | No | URL del servidor Eureka, sin `/eureka`. Por defecto `http://localhost:8761`. |

Opcionales:

- `WEBSITE_HOSTNAME`: hostname con el que se registra la instancia en Eureka (Azure App Service lo define solo). Por defecto `localhost`.
- `SERVER_PORT`: puerto HTTP. Por defecto `8080`, el mismo que Procedures; si corres ambos en local, usa otro (por ejemplo `8082`).

Puedes ponerlas en un archivo `.env` en la raiz del proyecto (lo lee `spring-dotenv`; `.env` esta en `.gitignore`):

```properties
DB_HOST=aws-0-us-east-1.pooler.supabase.com
SUPABASE_PROJECT_REF=xxxxxxxxxxxx
DB_USERNAME=postgres.xxxxxxxxxxxx
DB_PASSWORD=********
EUREKA_URL=http://localhost:8761
SERVER_PORT=8082
```

## Correr en local

Requisitos: JDK 17 en `JAVA_HOME`. No hace falta Maven instalado (se usa el wrapper).

```bash
# Tests (usan H2, no tocan Supabase)
./mvnw clean verify

# Levantar el servicio
./mvnw spring-boot:run
```

En Windows usa `mvnw.cmd` en lugar de `./mvnw`.

Con el servicio arriba:

```bash
curl http://localhost:8082/api/v1/defects/user/9101
```

Nota sobre Eureka en local: `application.properties` registra la instancia con `https` y puerto `443` (pensado para Azure App Service). Si quieres que el API Gateway local llegue a este servicio via Eureka, sobreescribe esos valores al arrancar:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--eureka.instance.secure-port-enabled=false --eureka.instance.non-secure-port-enabled=true"
```

## Indice de Endpoints

| Modulo | Operacion | Metodo | Ruta | Auth |
| --- | --- | --- | --- | --- |
| Defects | Crear registro de defecto | `POST` | `/api/v1/defects` | Publico |
| Defects | Listar defectos por usuario | `GET` | `/api/v1/defects/user/{userId}` | Publico |
| Defects | Obtener defecto por id | `GET` | `/api/v1/defects/user/{userId}/{defectId}` | Publico |

---

<h2 style="color:#0A58CA;">Defects - Crear registro de defecto</h2>

<p>
<span style="background:#198754;color:#ffffff;padding:4px 10px;border-radius:999px;font-weight:700;">POST</span>
<code>/api/v1/defects</code>
</p>

### Header

| Campo | Descripcion |
| --- | --- |
| `Content-Type` | Requerido. Usar `application/json`. |
| `Accept` | Opcional. Recomendado `application/json`. |

### Parametro (body)

| Campo | Tipo | Descripcion |
| --- | --- | --- |
| `userId` | `number` | Requerido. |
| `coffeeDisplayName` | `string` | Requerido. Nombre visible del cafe. |
| `coffeeRegion` | `string` | `Opcional`. Region. |
| `coffeeVariety` | `string` | `Opcional`. Variedad. |
| `coffeeTotalWeight` | `number` | `Opcional`. Si se envia, no puede ser negativo. |
| `name` | `string` | Requerido. Nombre del defecto. |
| `defectType` | `string` | Requerido. Tipo de defecto. |
| `defectWeight` | `number` | Requerido. Debe ser mayor que cero. |
| `percentage` | `number` | Requerido. Entre 0 y 100 inclusive. |
| `probableCause` | `string` | Requerido. |
| `suggestedSolution` | `string` | Requerido. |

### Request Example

```json
{
  "userId": 9101,
  "coffeeDisplayName": "Cafe funcional",
  "coffeeRegion": "Cusco",
  "coffeeVariety": "Bourbon",
  "coffeeTotalWeight": 500.0,
  "name": "Astringencia",
  "defectType": "Sabor",
  "defectWeight": 5.0,
  "percentage": 1.0,
  "probableCause": "Extraccion alta",
  "suggestedSolution": "Ajustar molienda"
}
```

### Success Response

**HTTP 201**

```json
{
  "id": 1,
  "userId": 9101,
  "coffeeDisplayName": "Cafe funcional",
  "coffeeRegion": "Cusco",
  "coffeeVariety": "Bourbon",
  "coffeeTotalWeight": 500.0,
  "name": "Astringencia",
  "defectType": "Sabor",
  "defectWeight": 5.0,
  "percentage": 1.0,
  "probableCause": "Extraccion alta",
  "suggestedSolution": "Ajustar molienda"
}
```

### Default Response

| Campo | Tipo | Descripcion |
| --- | --- | --- |
| `id` | `number` | Identificador del registro. |
| `userId` | `number` | Propietario. |
| `coffeeDisplayName` | `string` | Nombre del cafe. |
| `coffeeRegion` | `string` | Region. |
| `coffeeVariety` | `string` | Variedad. |
| `coffeeTotalWeight` | `number` | Peso total del lote. |
| `name` | `string` | Nombre del defecto. |
| `defectType` | `string` | Tipo. |
| `defectWeight` | `number` | Peso del defecto. |
| `percentage` | `number` | Porcentaje (0-100). |
| `probableCause` | `string` | Causa probable. |
| `suggestedSolution` | `string` | Solucion sugerida. |

### Extra Response

No aplica.

### Error 4xx

Error 400:

```json
{
  "message": "El porcentaje debe estar entre 0 y 100."
}
```

```json
{
  "message": "No se pudo crear el defecto"
}
```

---

<h2 style="color:#0A58CA;">Defects - Listar defectos por usuario</h2>

<p>
<span style="background:#0A58CA;color:#ffffff;padding:4px 10px;border-radius:999px;font-weight:700;">GET</span>
<code>/api/v1/defects/user/{userId}</code>
</p>

### Header

| Campo | Descripcion |
| --- | --- |
| `Accept` | Opcional. Recomendado `application/json`. |

### Parametro (path)

| Campo | Tipo | Descripcion |
| --- | --- | --- |
| `userId` | `number` | Requerido. |

### Request Example

```http
GET /api/v1/defects/user/9101
Accept: application/json
```

### Success Response

**HTTP 200**

Arreglo de `DefectResource`.

### Default Response

Igual que **Defects - Crear registro de defecto**.

### Extra Response

No aplica.

### Error 4xx

Listado vacio devuelve `[]`.

---

<h2 style="color:#0A58CA;">Defects - Obtener defecto por id</h2>

<p>
<span style="background:#0A58CA;color:#ffffff;padding:4px 10px;border-radius:999px;font-weight:700;">GET</span>
<code>/api/v1/defects/user/{userId}/{defectId}</code>
</p>

### Header

| Campo | Descripcion |
| --- | --- |
| `Accept` | Opcional. Recomendado `application/json`. |

### Parametro (path)

| Campo | Tipo | Descripcion |
| --- | --- | --- |
| `userId` | `number` | Requerido. |
| `defectId` | `number` | Requerido. |

### Request Example

```http
GET /api/v1/defects/user/9101/1
Accept: application/json
```

### Success Response

**HTTP 200**

Objeto `DefectResource`.

### Default Response

Igual que **Defects - Crear registro de defecto**.

### Extra Response

No aplica.

### Error 4xx

Error 404:

```json
{
  "message": "Defecto no encontrado"
}
```

## Notas de Implementacion

- `DefectsController` solo expone create y lecturas; no hay `PUT` ni `DELETE`.
- Mensaje 404 fijo: `Defecto no encontrado` (definido en `DefectNotFoundException`).
- La tabla en BD es `defects` (naming strategy snake_case + plural).
- Pruebas: `DefectTest`, `DefectsControllerIntegrationTest` y `DefectFunctionalTest`.

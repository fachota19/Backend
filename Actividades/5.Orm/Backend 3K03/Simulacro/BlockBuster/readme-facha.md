# 🎬 BlockBuster – Simulacro de Parcial (Backend de Aplicaciones)

## 📖 Descripción

**BlockBuster** es una aplicación de consola desarrollada en **Java (JPA + Hibernate + H2)** que simula la gestión de un catálogo de películas.  
El objetivo del trabajo es aplicar los conceptos de **persistencia con JPA**, **servicios**, **repositorios**, y **procesamiento de datos con Streams**, **sin usar Spring ni Spring Data**.

El sistema permite cargar películas desde un archivo CSV y ejecutar diversas consultas sobre los datos almacenados.

---

## 🏗️ Estructura del Proyecto

BlockBuster/
│
├── pom.xml
├── BlockBuster.iml
├── src/
│ ├── main/
│ │ ├── java/ar/edu/utnfrc/backend/
│ │ │ ├── Main.java
│ │ │ ├── menu/
│ │ │ │ └── Menu.java
│ │ │ ├── model/
│ │ │ │ ├── Pelicula.java
│ │ │ │ ├── Director.java
│ │ │ │ ├── Genero.java
│ │ │ │ └── Clasificacion.java
│ │ │ ├── repository/
│ │ │ │ ├── DbContext.java
│ │ │ │ ├── DbInit.java
│ │ │ │ ├── Repository.java
│ │ │ │ ├── PeliculaRepository.java
│ │ │ │ ├── GeneroRepository.java
│ │ │ │ └── DirectorRepository.java
│ │ │ └── service/
│ │ │ ├── PeliculaService.java
│ │ │ ├── GeneroService.java
│ │ │ └── DirectorService.java
│ │ └── resources/
│ │ ├── META-INF/persistence.xml
│ │ ├── sql/db-schema.sql
│ │ └── peliculas.csv
│ └── test/
│ └── ...

---

## ⚙️ Tecnologías Utilizadas

- **Java 17+**
- **Maven**
- **Jakarta Persistence (JPA 3.1)**
- **Hibernate ORM 6.4**
- **Base de datos H2 (en memoria)**
- **Lombok (para simplificar entidades y getters/setters)**

---

## 🧩 Funcionalidades

El sistema cumple con las **5 consignas obligatorias** del simulacro:

### 1️⃣ Cargar Películas desde CSV
Lee el archivo `src/main/resources/peliculas.csv`, parsea los campos:
titulo;fechaEstreno;precioBaseAlquiler;clasificacion;genero;director

y los persiste como entidades `Pelicula`, `Genero` y `Director`.

🟢 Resultado esperado:


✅ Películas cargadas correctamente.


---

### 2️⃣ Listar películas por director
Permite ingresar el nombre de un director (por ejemplo `Ana Torres`) y listar todas sus películas.  
Si se deja el campo vacío, agrupa y muestra todas las películas por director.

🟢 Ejemplo:


Director: Ana Torres (total: 6)

Pelicula 012 | 2015-03-21 | $18.94 | Acción

Pelicula 037 | 2025-03-01 | $15.91 | Aventura


---

### 3️⃣ Cantidad de películas recientes (≤ 365 días)
Calcula cuántas películas se estrenaron en el último año usando `LocalDate` y `ChronoUnit.DAYS`.

🟢 Ejemplo:


Películas recientes: 30


---

### 4️⃣ Promedio de precio por género
Agrupa las películas por género y calcula el **promedio del precio base de alquiler** de cada uno.

🟢 Ejemplo:


--- Promedio de precio base por género ---
Acción → $13.36
Romance → $14.36
Ciencia Ficción → $13.42
Comedia → $12.15
Documental → $11.66


---

### 5️⃣ Mostrar película más reciente
Muestra los datos de la película con la fecha de estreno más actual registrada en la base.

🟢 Ejemplo:


Más reciente:

Título: Pelicula 027

Fecha estreno: 2025-07-29

Director: Pilar Carrizo

Género: Animación

Precio base: $6.84


---

## 🧠 Lógica y Arquitectura

El sistema se basa en una arquitectura **en capas:**

| Capa | Descripción |
|------|--------------|
| `model` | Entidades JPA (mapeo ORM con Hibernate). |
| `repository` | Acceso a datos con `EntityManager` (consultas, persistencia). |
| `service` | Lógica de negocio y operaciones con Streams. |
| `menu` | Manejo del menú interactivo de consola. |
| `Main` | Punto de entrada; inicializa el contexto y lanza el menú. |

La conexión a la base se maneja a través del **patrón Singleton** (`DbContext`), asegurando una única instancia de `EntityManager` para toda la aplicación.

---

## 🗃️ Base de Datos

- Motor: **H2 en memoria**
- Configuración: `persistence.xml`
- Creación automática: `hibernate.hbm2ddl.auto = update`
- Script DDL adicional: `src/main/resources/sql/db-schema.sql`

Tablas generadas:
- `DIRECTOR`
- `GENERO`
- `PELICULA`

---

## 🚀 Ejecución

### Compilar el proyecto
```bash
mvn clean compile

Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="ar.edu.utnfrc.backend.Main"

Navegar el menú
1) Cargar Películas desde CSV
2) Listar películas por director
3) Cantidad de películas recientes (<= 365 días)
4) Promedio de precio por género
5) Mostrar película más reciente
0) Salir

🧹 Posibles mejoras

Normalizar acentos en nombres de género/director para evitar duplicados (“Acción” vs “AcciÃ³n”).

Persistencia en archivo (jdbc:h2:file:) para mantener datos entre ejecuciones.

Exportar reportes de promedio y películas recientes a CSV.

👨‍💻 Autor

Juan Negri
Ingeniería en Sistemas de Información – UTN FRC
Materia: Backend de Aplicaciones
Año: 2025
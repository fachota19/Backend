# 🧩 Simulacro JPA – Backend de Aplicaciones (UTN-FRC)

## 📘 Descripción general
Este proyecto implementa la práctica propuesta en el **pre-enunciado del simulacro de la Unidad ORM/JPA**, utilizando **JPA puro con Hibernate** y una base de datos **H2 en memoria**.

La aplicación inicializa automáticamente la base ejecutando el script `ddl.sql`, mapea las entidades de acuerdo con las tablas definidas y permite realizar operaciones **CRUD** y **consultas filtradas** sobre los datos, todo **sin utilizar Spring ni Spring Data**.

------------------------------------------------------------------------------------------------------------

## ⚙️ Tecnologías utilizadas
| Componente | Versión / Tecnología |
|-------------|----------------------|
| Lenguaje | Java 21 |
| Build Tool | Apache Maven |
| Base de datos | H2 (modo memoria) |
| ORM / JPA Provider | Hibernate 6 |
| API de Persistencia | Jakarta Persistence 3.1 |
| Testing | JUnit 5 (Jupiter) |

------------------------------------------------------------------------------------------------------------

## 🗂️ Estructura del proyecto
simulacro-jpa/
├─ pom.xml # Configuración de dependencias y plugins
├─ src/
│ ├─ main/java/ar/edu/utn/frc/
│ │   ├─ App.java # Punto de entrada (Main)
│ │   ├─ SchemaInitializer.java # Ejecuta ddl.sql al iniciar
│ │   ├─ dao/ # DAOs propios (sin SpringData)
│ │   ├─ domain/ # Entidades JPA + Enum ESRB
│ │   └─ infrastructure/ # Utilidades (JpaUtil, CsvJuegoImporter)
│ ├─ main/resources/
│ │   ├─ META-INF/persistence.xml # Configuración de unidad de persistencia
│ │   └─ ddl.sql # Script de creación de esquema
│ └─ test/java/ar/edu/utn/frc/
│     ├─ BaseDaoTest.java # Inicializa esquema antes de tests
│     └─ JuegoDaoTest.java # Prueba CRUD y consultas básicas

------------------------------------------------------------------------------------------------------------

## 🧱 Funcionalidades implementadas

| Requisito del enunciado | Implementación |
|--------------------------|----------------|
| Ejecución automática del `ddl.sql` | Clase `SchemaInitializer` ejecuta el script vía JDBC antes de iniciar JPA. |
| Entidades JPA mapeadas al esquema | `Genero`, `Desarrollador`, `Plataforma`, `Juego`. |
| Relaciones entre tablas | `Juego` → `Genero`, `Desarrollador`, `Plataforma` (`@ManyToOne`). |
| Enum `CLASIFICACION_ESRB` | `ClasificacionEsrb` + `ClasificacionEsrbConverter`. |
| Capa de acceso a datos (DAO) | CRUD + consultas con JPQL en `JuegoDao`. |
| Inserción y consultas desde main | `App.java` crea catálogos, inserta juegos y ejecuta consultas. |
| Carga opcional desde CSV | `CsvJuegoImporter` (no requerida, lista para usar). |
| Tests JUnit | `JuegoDaoTest` valida CRUD y consultas sobre H2. |

------------------------------------------------------------------------------------------------------------

## ▶️ Cómo ejecutar la aplicación

1. **Compilar y ejecutar:**
   ```bash
   mvn -clean compile exec:java
   [DDL] Ejecutado OK.
-- Juegos en PS5 --
 * Elden Ring
 * The Last of Us Part I
-- Juegos ESRB M (primeros 2) --
 * Elden Ring
 * The Last of Us Part I
-- Total de juegos: 2

------------------------------------------------------------------------------------------------------------

## 🧪 Cómo ejecutar los tests

Desde la raíz del proyecto (donde está pom.xml):
mvn test

Salida esperada:
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS

------------------------------------------------------------------------------------------------------------

El test JuegoDaoTest valida las operaciones:

## CREATE: Inserta catálogos y juegos con relaciones FK.

## READ: Busca por ID y por plataforma.

## UPDATE: Modifica el rating y guarda cambios.

## DELETE: Elimina el registro y verifica su ausencia.
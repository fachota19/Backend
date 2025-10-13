# 🖼️ Museo de Obras de Arte

## 📘 Descripción general

Este proyecto implementa una **aplicación Java con JPA (Hibernate + H2)** que gestiona información sobre **obras de arte, autores, museos y estilos artísticos**.  
El sistema lee datos desde un archivo CSV, los persiste en una base de datos relacional H2, realiza análisis mediante *Streams API*, genera reportes y ejecuta consultas JPQL para obtener información estadística.

El objetivo del trabajo práctico es demostrar el uso integrado de:
- **Lectura y procesamiento de archivos**
- **Persistencia de entidades con JPA / Hibernate**
- **Consultas a base de datos (JPQL)**
- **Procesamiento de datos con Stream API**
- **Generación de reportes CSV**

---

## 🧩 Estructura del proyecto

museo-obras-arte-app/
│
├── pom.xml → Archivo de configuración Maven (dependencias y plugins)
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/frc/isi/museo/
│ │ │ ├── app/
│ │ │ │ └── App.java → Clase principal (punto de entrada)
│ │ │ │
│ │ │ ├── dao/ → Capa de acceso a datos (DAOs)
│ │ │ │ ├── AutorDAO.java
│ │ │ │ ├── EstiloArtisticoDAO.java
│ │ │ │ ├── MuseoDAO.java
│ │ │ │ └── ObraArtisticaDAO.java
│ │ │ │
│ │ │ ├── entidades/ → Entidades JPA mapeadas a tablas
│ │ │ │ ├── Autor.java
│ │ │ │ ├── EstiloArtistico.java
│ │ │ │ ├── Museo.java
│ │ │ │ └── ObraArtistica.java
│ │ │ │
│ │ │ ├── utils/
│ │ │ │ ├── Acciones.java → Operaciones de lectura, cálculos y reportes
│ │ │ │ └── ConsultasJPQL.java → Consultas SQL orientadas a objetos (JPQL)
│ │ │
│ │ └── resources/
│ │ ├── files/
│ │ │ ├── obras.csv → Fuente de datos inicial
│ │ │ ├── reporte_estilos.csv → Reporte generado automáticamente
│ │ │ └── META-INF/
│ │ │ └── persistence.xml → Configuración de la unidad de persistencia
│ │ └── logging.properties → Configuración para silenciar logs de Hibernate
│ │
│ └── test/ → (opcional) Pruebas unitarias futuras
│
└── data/
└── museo.mv.db → Base de datos H2 generada automáticamente

---

## ⚙️ Dependencias principales

El proyecto utiliza las siguientes tecnologías:
- **Jakarta Persistence (JPA 3.1)** → manejo ORM
- **Hibernate ORM 7.0.2.Final** → implementación JPA
- **H2 Database Engine 2.3.232** → base de datos embebida
- **Maven Exec Plugin 3.5.0** → ejecución desde consola
- **Lombok (opcional)** → generación automática de getters/setters

---

## 🚀 Ejecución del proyecto

### 🔹 1. Compilar el proyecto
Desde la raíz del proyecto:

```bash

mvn clean compile

🔹 2. Ejecutar la aplicación principal
mvn exec:java -Dexec.mainClass="com.frc.isi.museo.app.App"

🔹 3. Resultado esperado

En consola verás:

La inicialización de Hibernate

Mensaje: Se guardaron 300 obras en la base de datos.

Totales de montos asegurados

Listado de obras destacadas

Reporte generado: reporte_estilos.csv

Y al final:

===== CONSULTAS JPQL =====
===== MONTOS ASEGURADOS (JPQL) =====
...
BUILD SUCCESS

🧠 Etapas de funcionamiento
🩵 Etapa 1 – Lectura del archivo CSV

Clase involucrada: Acciones.java

Fuente: src/main/resources/files/obras.csv

Se leen los datos de cada obra (nombre, año, autor, museo, estilo, etc.)

Se transforman en objetos ObraArtistica con sus relaciones (Autor, Museo, EstiloArtistico).

🧩 Etapa 2 – Persistencia de entidades (JPA / Hibernate)

Clase principal: App.java

DAOs: AutorDAO, MuseoDAO, EstiloArtisticoDAO, ObraArtisticaDAO

Hibernate crea automáticamente las tablas:

autores
museos
estilos_artistico
obras_artisticas


Se persisten los objetos evitando duplicados (mediante búsquedas por nombre).

⚖️ Etapa 3 – Procesamiento con Stream API

Archivo: Acciones.java

Se agrupan y filtran las obras para:

Calcular montos totales asegurados.

Generar estadísticas por estilo.

Identificar obras con daño parcial superior al promedio.

📊 Etapa 4 – Generación de reportes

Se crea un archivo CSV con resumen:

Estilo,Cantidad
Impresionismo,35
Barroco,77
...


Guardado en: src/main/resources/files/reporte_estilos.csv

🧮 Etapa 5 – Consultas JPQL

Clase: ConsultasJPQL.java

Se ejecutan consultas orientadas a objetos:

Total asegurado por tipo de daño

Cantidad de obras por estilo

Obras con monto mayor al promedio

Obras pertenecientes a un museo específico

Estas consultas se ejecutan directamente contra la base de datos persistida (H2).

🗄️ Base de datos

Tipo: H2 embebida (archivo .mv.db)

Ubicación: ./data/museo.mv.db

Se genera automáticamente al ejecutar la app.

Configurada en persistence.xml:

<property name="jakarta.persistence.jdbc.url" value="jdbc:h2:file:./data/museo;AUTO_SERVER=TRUE"/>
<property name="hibernate.hbm2ddl.auto" value="create"/>


Podés abrirla con H2 Console o DBeaver usando:

JDBC URL: jdbc:h2:file:./data/museo
User: sa
Password: (vacío)

🧾 Resultado final esperado
✅ Se guardaron 300 obras en la base de datos.

===== MONTOS ASEGURADOS =====
Total por destrucción total: $414926162.00
Total por daño parcial:      $374582677.00
Total asegurado general:     $789508839.00

Generando reporte por estilo artístico...
✅ Archivo 'reporte_estilos.csv' generado correctamente.

===== CONSULTAS JPQL =====
Barroco          → 77 obras
Impresionismo    → 35 obras
...

👨‍💻 Autor
Juan Negri
Backend de Aplicaciones — UTN FRC — 3K03B
Año: 2025
Entrega: ORM + JPA + Streams + JPQL
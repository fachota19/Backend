🧾 Gestión de Empleados – Simulacro Backend 3K1
📌 Descripción general

Este proyecto consiste en una aplicación de consola en Java que permite gestionar empleados de una empresa.
El sistema implementa lectura desde archivo CSV, persistencia en base de datos H2 en memoria mediante JPA/Hibernate, y consultas diversas sobre los datos almacenados.

El programa se ejecuta con Maven utilizando el comando:

mvn exec:java


y muestra un menú interactivo con las distintas opciones de gestión.

🧱 Arquitectura del proyecto

El proyecto sigue una estructura modular dividida por capas, aplicando las buenas prácticas vistas en la materia:

gestion-empleados-app/
│
├── pom.xml
├── empleados.csv
└── src/
    ├── main/
    │   ├── java/com/empresa/app/
    │   │   ├── app/
    │   │   │   └── App.java                  # Clase principal con el menú de opciones
    │   │   ├── modelo/
    │   │   │   ├── Empleado.java             # Entidad principal
    │   │   │   ├── Departamento.java         # Entidad relacionada (ManyToOne)
    │   │   │   └── Puesto.java               # Entidad relacionada (ManyToOne)
    │   │   ├── persistencia/
    │   │   │   └── JpaUtil.java              # Configuración del EntityManager
    │   │   └── servicio/
    │   │       └── EmpleadoService.java      # Lógica de negocio y operaciones CRUD
    │   └── resources/
    │       └── META-INF/
    │           └── persistence.xml           # Configuración JPA / H2 / Hibernate
    └── test/
        └── java/                             # (Vacío)

⚙️ Dependencias principales

El proyecto usa las siguientes librerías, configuradas en el pom.xml:

Dependencia	Versión	Descripción
jakarta.persistence-api	3.1.0	API de JPA
org.hibernate.orm:hibernate-core	6.3.1.Final	Implementación de JPA
com.h2database:h2	2.2.224	Base de datos embebida en memoria
org.projectlombok:lombok	1.18.30	(Opcional) Generación automática de getters/setters
org.codehaus.mojo:exec-maven-plugin	3.1.0	Permite ejecutar la app con mvn exec:java
🧩 Configuración de la base de datos

El archivo persistence.xml establece una conexión a una base de datos H2 en memoria, la cual se crea y destruye automáticamente en cada ejecución.

<property name="jakarta.persistence.jdbc.url" value="jdbc:h2:mem:empresaDB;DB_CLOSE_DELAY=-1"/>
<property name="hibernate.hbm2ddl.auto" value="create-drop"/>


Hibernate se encarga de generar las tablas según las entidades:

Empleado

Departamento

Puesto

Cada ejecución del programa crea el esquema, carga los datos, y al finalizar lo destruye.

🧮 Modelo de datos

El modelo de clases se compone de tres entidades principales:

classDiagram
    class Empleado {
        int id
        String nombre
        int edad
        LocalDate fechaIngreso
        double salario
        boolean empleadoFijo
        +calcularSalarioFinal()
    }

    class Departamento {
        int id
        String nombre
    }

    class Puesto {
        int id
        String nombre
    }

    Empleado --> Departamento
    Empleado --> Puesto

Relaciones:

Un empleado pertenece a un departamento (@ManyToOne).

Un empleado ocupa un puesto (@ManyToOne).

El método calcularSalarioFinal() suma un 8% adicional si el empleado es fijo.

🧠 Flujo general de ejecución

Al iniciar, Hibernate crea las tablas según las entidades.

El programa muestra el menú principal en consola.

El usuario puede cargar los datos desde el archivo empleados.csv.

Los registros se leen, se mapean a objetos Empleado, Departamento y Puesto, y se guardan en la base.

Las demás opciones del menú permiten realizar consultas y generar reportes.

📋 Menú de opciones
=== GESTIÓN DE EMPLEADOS ===
1. Cargar empleados desde CSV
2. Listar empleados
3. Promedio de salario por departamento
4. Empleado con mayor antigüedad
5. Cantidad de empleados fijos vs temporales
6. Exportar resumen CSV (por puesto)
7. Salir

🔹 Opción 1 – Cargar empleados desde CSV

Lee el archivo empleados.csv y persiste los registros.

Ejemplo de archivo:

nombre,edad,fechaIngreso,salario,empleadoFijo,departamento,puesto
Juan Perez,35,2018-03-10,450000,true,Contabilidad,Analista Senior
Maria Gomez,28,2020-07-01,320000,false,Recursos Humanos,Asistente
Carlos Ruiz,42,2015-05-22,600000,true,IT,Programador Senior
Lucia Diaz,31,2019-02-15,380000,false,IT,Tester QA


Salida esperada:

Empleados cargados correctamente.

🔹 Opción 2 – Listar empleados

Muestra todos los empleados registrados, con su salario final calculado:

Juan Perez (35) - Contabilidad - Analista Senior - Salario final: 486000.00
Maria Gomez (28) - Recursos Humanos - Asistente - Salario final: 320000.00
Carlos Ruiz (42) - IT - Programador Senior - Salario final: 648000.00
Lucia Diaz (31) - IT - Tester QA - Salario final: 380000.00

🔹 Opción 3 – Promedio de salario por departamento
--- Promedio de salario por departamento ---
Contabilidad: 450000.00
IT: 490000.00
Recursos Humanos: 320000.00

🔹 Opción 4 – Empleado con mayor antigüedad
Empleado con mayor antigüedad: Carlos Ruiz (2015-05-22)

🔹 Opción 5 – Cantidad de empleados fijos vs temporales
Empleados fijos: 2
Empleados temporales: 2

🔹 Opción 6 – Exportar resumen CSV por puesto

Genera un archivo resumen_puestos.csv con la cantidad de empleados agrupados por puesto:

Puesto,Cantidad
Analista Senior,1
Asistente,1
Programador Senior,1
Tester QA,1

🧪 Modo de ejecución

Desde la raíz del proyecto:

mvn clean compile exec:java


Luego, seguir las opciones del menú en la consola.

🧰 Tecnologías utilizadas
Tecnología	Descripción
Java 17	Lenguaje principal
Maven	Compilación y gestión de dependencias
JPA / Hibernate	Mapeo objeto-relacional
H2 Database	Base de datos embebida en memoria
CSV	Fuente de datos inicial
Lombok (opcional)	Simplificación de código boilerplate
🧩 Criterios de evaluación cumplidos
Criterio	Estado
Diseño del modelo de clases con POO	✅
Uso de JPA e Hibernate para persistencia	✅
Lectura de archivo CSV e inserción en DB	✅
Consultas JPQL (promedios, filtros, conteos)	✅
Exportación de reportes CSV	✅
Organización modular por paquetes	✅
Ejecución automática con Maven	✅
👨‍💻 Autor

Juan Negri
Ingeniería en Sistemas de Información – UTN FRC
Materia: Backend de Aplicaciones (3K1)
Simulacro de Parcial – ORM / JPA / Hibernate
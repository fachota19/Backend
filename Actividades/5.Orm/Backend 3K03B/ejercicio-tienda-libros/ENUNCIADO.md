# Ejercicio Práctico: Gestión de Inventario de una Tienda de Libros
## Objetivo

El objetivo de este ejercicio es desarrollar una aplicación de backend en Java que simule la gestión de inventario de una tienda de libros. Deberás aplicar conceptos clave como la lectura de archivos, el mapeo de entidades con JPA y operaciones de base de datos DML utilizando una base de datos embebida H2.

## Escenario

Eres el desarrollador principal de una pequeña tienda de libros que necesita un sistema simple para inicializar su inventario. El equipo de ventas te ha proporcionado un archivo CSV con la información de los libros que deben ser cargados en el sistema.

Tu tarea es:

1.    Leer los datos del archivo CSV.

2.    Mapear estos datos a un modelo de objetos.

3.    Persistirlos en una base de datos H2.

4.    Implementar la lógica para consultar, actualizar y eliminar libros.

## Requisitos

1.    **Entidades de Negocio**: Deberás modelar y mapear al menos 3 entidades de negocio:

      * Libro: Representa un libro en el inventario. Deberá tener propiedades como `isbn`, `titulo`, `stock`, `precio`, y relaciones con `Autor` y `Editorial`.

      * `Autor`: Representa al autor de un libro. Deberá tener propiedades como `nombre` y `apellido`.

      * `Editorial`: Representa la editorial que publica un libro. Deberá tener una propiedad como `nombre`.

2.  **Tecnologías**:

      * **Persistencia**: Utiliza JPA con un proveedor como Hibernate.

      *  **Base de Datos**: Configura y usa una base de datos H2 en modo embebido (`in-memory`).

      *  **Lectura de Archivos**: Implementa la lógica para leer y parsear un archivo CSV.

3.    **Funcionalidades**:

      *  **Carga de Datos (DML - INSERT)**: Implementa un método que lea el archivo `libros.csv` (adjunto más abajo) y persista cada libro, autor y editorial en la base de datos. Si un autor o editorial ya existe, no debe crearse uno nuevo.

      * **Consulta (DML - SELECT)**: Crea un método para obtener y mostrar todos los libros en el inventario.

      * **Actualización (DML - UPDATE)**: Implementa un método para actualizar el stock de un libro específico, identificado por su ISBN.

      * **Eliminación (DML - DELETE)**: Implementa un método para eliminar un libro de la base de datos, identificado por su ISBN.


## Estructura del Proyecto y Dependencias

Se espera que la solución se organice en un proyecto Maven.
Las dependencias mínimas requeridas son: `JPA (jakarta.persistence)`, `Hibernate`, `H2 Database`, `JUnit 5` (para las pruebas).

## Archivo de Datos: `libros.csv`

Crea un archivo llamado `libros.csv` en la raíz de tu proyecto o en el directorio que elijas para la carga.

### Datos de ejemplo

    isbn,titulo,autor_nombre,autor_apellido,editorial_nombre,stock,precio
    978-0134685991,Clean Code,Robert,Martin,Prentice Hall,15,59.99
    978-0321765723,The Pragmatic Programmer,Andrew,Hunt,Addison-Wesley,10,45.50
    978-0132350884,The Clean Coder,Robert,Martin,Prentice Hall,8,55.00
    978-1934356073,The Go Programming Language,Alan,Donovan,Addison-Wesley,20,62.75
    978-0321721590,Effective Java,Joshua,Bloch,Addison-Wesley,12,50.25


# Ítem Adicional

**Ampliación del Ejercicio:**

1. Agrega dos nuevas entidades de negocio a tu modelo. Puedes elegir entre categorías como `Cliente`, `Venta`, `Categoria`, `Sucursal`, etc. Cada entidad debe tener al menos dos atributos propios y, si corresponde, una relación con las entidades existentes.

2. Implementa tres operaciones nuevas en la clase `LibroService`:
   - Una operación para consultar libros por una de las nuevas entidades (por ejemplo, listar libros por categoría o por sucursal).
   - Una operación para actualizar un atributo de una de las nuevas entidades (por ejemplo, modificar el nombre de una sucursal o la dirección de un cliente).
   - Una operación para eliminar una instancia de una de las nuevas entidades (por ejemplo, eliminar una categoría o un cliente).

3. Documenta en el código y en los tests cómo se utilizan estas nuevas operaciones y entidades.

---

**SE SOLICITA:**
- Modelar las dos nuevas entidades en Java, con sus atributos y relaciones.
- Implementar las tres operaciones adicionales en `LibroService`.
- Agregar pruebas unitarias que validen el correcto funcionamiento de estas operaciones.
- Comentar el código para explicar la lógica de cada operación.
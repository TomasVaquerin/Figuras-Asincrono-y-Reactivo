# Proyecto: Gestor de Figuras

Este proyecto es un gestor de figuras geométricas con una implementación en **Kotlin** que permite manejar los datos
tanto de manera **asíncrona** como en modo **reactivo**. Está diseñado para gestionar figuras almacenadas en una base de
datos H2 y permite realizar operaciones CRUD, realizar backups y trabajar con datos leídos desde archivos CSV.

## Contenido

- [Instalación y Configuración](#instalación-y-configuración)
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Modos de Ejecución](#modos-de-ejecución)
    - [Versión Asíncrona](#versión-asíncrona)
    - [Versión Reactiva](#versión-reactiva)
- [Consultas Implementadas](#consultas-implementadas)
- [Pruebas Unitarias](#pruebas-unitarias)
- [Servicio de Notificaciones](#servicio-de-notificaciones)
- [Logger](#logger)
- [Importación y Exportación de Datos](#importación-y-exportación-de-datos)

## Instalación y Configuración

### Requisitos

- Java 21+
- Base de datos H2
- Dependencias:
    - HikariCP (para la versión asíncrona)
    - R2DBC (para la versión reactiva)
    - Project Reactor / RxJava (para la versión reactiva)
    - JUnit / Mockito (para las pruebas)
    - Logger: Se usa un sistema de logging para registrar todas las operaciones.

### Configuración de la Base de Datos

- Utiliza una base de datos H2 en archivo llamada `figuras`.
- El archivo de configuración debe contener las propiedades de la conexión.
- En la versión asíncrona, configura el pool de conexiones con **HikariCP**.
- En la versión reactiva, configura el acceso a la base de datos con **R2DBC**.

## Descripción del Proyecto

El proyecto gestiona una colección de figuras con los siguientes atributos:

- `id`: autonumérico (PRIMARY KEY)
- `cod`: UUID, generado automáticamente si no se proporciona
- `MyId`: `Long`, generado automáticamente por un `IdGenerator`
- `nombre`: String de hasta 255 caracteres
- `modelo`: Enum (`MARVEL`, `DISNEY`, `ANIME`, `DEPORTE`, `MUSICA`, `OTROS`)
- `precio`: Decimal con dos decimales
- `fecha_lanzamiento`: Fecha en formato `YYYY-MM-DD`
- `created_at`: Timestamp generado automáticamente al crear
- `updated_at`: Timestamp generado automáticamente al actualizar

## Modos de Ejecución

### Versión Asíncrona

1. **HikariCP**:
    - Se utiliza **HikariCP** para gestionar el pool de conexiones en la base de datos H2.

2. **Repositorio CRUD**:
    - Soporta operaciones CRUD: `create`, `read`, `update`, `delete`.
    - Incluye un método adicional `findByModelo` para filtrar las figuras por su modelo.

3. **Servicio Asíncrono**:
    - Implementa un servicio que gestiona una caché con un máximo de 10 elementos.
    - Maneja errores personalizados.
    - Ofrece un método para hacer backups en JSON y otro para importar datos desde CSV, ambos de manera asíncrona.

### Versión Reactiva

1. **R2DBC**:
    - Utiliza **R2DBC** para implementar el acceso a la base de datos H2 de forma reactiva.

2. **Repositorio CRUD Reactivo**:
    - Al igual que en la versión asíncrona, se implementan operaciones CRUD, además del método `findByModelo`.

3. **Servicio Reactivo**:
    - Utiliza una caché reactiva con un máximo de 10 elementos.
    - Gestiona errores personalizados y permite realizar backups en JSON e importación de CSV de forma reactiva.

## Consultas Implementadas

Tanto en la versión asíncrona como en la versión reactiva, se han implementado las siguientes consultas:

- **Figura más cara.**
- **Media de precio de Figuras.**
- **Figuras agrupados por modelos.**
- **Número de Figuras por modelos.**
- **Precio medio de las Figuras de Marvel.**
- **Precio medio de las figuras agrupadas por modelos.**
- **Figuras que han sido lanzados en 2023.**
- **Número de Figuras de Naruto.**
- **Listado de Figuras de Naruto.**

## Pruebas Unitarias
> [!WARNING]
> Pruebas Unitarias
> Se han implementado pruebas unitarias con **JUnit** y **Mockito** para las siguientes funcionalidades:
>
>- **Caché**
>- **Repositorio CRUD**
>- **Servicio de almacenamiento**
>- **Servicio de notificaciones**
>- **Servicio de figuras**
>
>Asegúrate de ejecutar las pruebas antes de implementar cambios significativos para evitar introducir errores.

## Servicio de Notificaciones

El servicio de notificaciones actúa en los siguientes eventos:

- **CREACIÓN**: Cuando se crea una nueva figura.
- **ACTUALIZACIÓN**: Cuando se actualiza una figura.
- **BORRADO**: Cuando se elimina una figura.

Cada notificación contiene el tipo de evento, un mensaje descriptivo y la fecha del evento.

## Logger

Se ha implementado un sistema de logging que registra las siguientes operaciones:

- Operaciones CRUD
- Operaciones de caché
- Errores y excepciones
- Importación y exportación de datos
- Notificaciones generadas

## Importación y Exportación de Datos

El proyecto incluye funcionalidades para importar datos desde archivos CSV y exportar los datos a archivos JSON:

> [!IMPORTANT]
> Importación CSV
>- Se pueden importar datos de figuras desde un archivo CSV con los siguientes campos:
>- `cod`: UUID
>- `nombre`: String
>- `modelo`: Enum (`MARVEL`, `DISNEY`, `ANIME`, `DEPORTE`, `MUSICA`, `OTROS`)
>- `precio`: Decimal con dos decimales
>- `fecha_lanzamiento`: Fecha en formato `YYYY-MM-DD`
   > -Se detectan y gestionan errores en los datos de entrada.

# ♻️ Gestión de Reciclaje InteligenteSistema de gestión de reciclaje con JAVA y MySQL

## Descripción del Proyecto

Este proyecto consiste en el desarrollo de una aplicación Java conectada a una base de datos MySQL para gestionar el reciclaje en distintas zonas urbanas. Permite registrar usuarios, puntos de reciclaje, materiales reciclables y actividades realizadas, generando estadísticas y rankings para fomentar la participación ciudadana.

## Funcionalidades principales

- Registro y gestión de usuarios.
- Registro de puntos de reciclaje (ubicación, capacidad, horario).
- Registro de materiales reciclables.
- Registro de actividades de reciclaje (qué, cuánto, cuándo y quién).
- Alertas por llenado de contenedores.
- Estadísticas y ranking de reciclaje por usuario y barrio.

## Tecnologías utilizadas

- Java
- MySQL
- JDBC
- JUnit (para pruebas unitarias)
- Git y GitHub

## Estructura del Proyecto
/src
 ├── model/           → Clases de datos (Usuario, Material, PuntoReciclaje, etc.)
 ├── dao/             → Clases DAO para acceso a la base de datos
 ├── controller/      → Lógica del sistema
 ├── test/            → Pruebas unitarias (JUnit)
 └── Main.java        → Punto de entrada del programa


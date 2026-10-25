# Proyecto Unidad 3 - Concesionaria de Autos (AutoMotors)

Aplicación web desarrollada con el **enfoque clásico de Java Web**, utilizando **JSP**, **JSTL**, **Servlets** y conexión a **Microsoft SQL Server** mediante **JDBC**.

---

## Caracteristicas Principales

1. **Tematica Distinta:** Concesionaria de Vehiculos deportivos y de lujo (**AutoMotors**), con campos:
   - `id`: Identificador autoincremental en la base de datos.
   - `marca`: Fabricante del vehículo (Ford, Porsche, BMW, Audi, etc.).
   - `modelo`: Modelo y versión específica del vehículo.
   - `foto`: Enlace directo a la imagen del vehículo.
   - `precio`: Precio en moneda nacional.
2. **Conexion a Base de Datos:** Conexión a **SQL Server** mediante el driver oficial de Microsoft (`mssql-jdbc`).
3. **Lectura de Datos (GET):** Consulta todos los vehículos registrados y los renderiza dinámicamente con **JSTL** (`<c:forEach>`, `<c:if>`, `<fmt:formatNumber>`).
4. **Guardado y Actualizacion de Datos (POST):** Formulario que almacena o actualiza vehículos mediante `PreparedStatement`.
5. **Diseño Visual Moderno:** Interfaz estilizada en `style.css` en tonos oscuros y rojo deportivo.

---

## Configuracion de la Base de Datos (SQL Server)

Ejecuta el siguiente script en SQL Server Management Studio (SSMS):

```sql
CREATE DATABASE Concesionaria;
USE Concesionaria;

CREATE TABLE Vehiculos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    marca NVARCHAR(50) NOT NULL,
    modelo NVARCHAR(50) NOT NULL,
    foto NVARCHAR(500) NOT NULL,
    precio INT NOT NULL
);

CREATE LOGIN AdminAutos WITH PASSWORD = 'Autos123', CHECK_POLICY = OFF;
USE Concesionaria;
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'AdminAutos')
BEGIN
    CREATE USER AdminAutos FOR LOGIN AdminAutos;
END
GO

ALTER ROLE db_datareader ADD MEMBER AdminAutos;
ALTER ROLE db_datawriter ADD MEMBER AdminAutos;
GO
```

---

## Estructura del Proyecto

```text
ProyectoUnidad3/
│
├── pom.xml                       <- Dependencias (mssql-jdbc, JSTL, Jakarta EE)
├── README.md                     <- Documentacion del proyecto
│
└── src/main/
    ├── java/
    │   ├── models/
    │   │   └── Vehiculo.java     <- Clase Modelo (POJO con atributos y métodos)
    │   └── servlets/
    │       └── SvVehiculo.java   <- Servlet controlador (doGet y doPost con JDBC)
    │
    └── webapp/
        ├── index.jsp             <- Vista principal con JSTL y formulario
        ├── style.css             <- Estilos visuales modernos
        └── WEB-INF/
            └── web.xml           <- Descriptor de despliegue web
```

---

## Como Ejecutar en Apache NetBeans

1. Abre **Apache NetBeans**.
2. Abre el proyecto `ProyectoUnidad3`.
3. Haz clic derecho sobre el proyecto y selecciona **Clean and Build**.
4. Haz clic derecho y selecciona **Run** (seleccionando tu servidor local Apache Tomcat).
5. Abre en tu navegador la URL:
   `http://localhost:8080/ProyectoUnidad3/` o navega directamente al servlet `http://localhost:8080/ProyectoUnidad3/SvVehiculo`.

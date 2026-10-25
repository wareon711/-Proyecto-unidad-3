<div align="center">

# 🏎️ Concesionaria Deportiva
### Sistema de Gestión de Vehículos de Alto Rendimiento

![Java](https://img.shields.io/badge/Java-17%20LTS-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-F05A28?style=for-the-badge&logo=eclipseide&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-10.1-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)
![SQL Server](https://img.shields.io/badge/SQL%20Server-2022-CC292B?style=for-the-badge&logo=microsoftsqlserver&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-3.9-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

<br/>

![Estado](https://img.shields.io/badge/Estado-Completado%20y%20Funcional-success?style=flat-square)
![Enfoque](https://img.shields.io/badge/Enfoque-Cl%C3%A1sico%20JSP%20%2B%20JSTL-blue?style=flat-square)
![CRUD](https://img.shields.io/badge/CRUD-Create%20%7C%20Read%20%7C%20Update%20%7C%20Delete-red?style=flat-square)

<p align="center">
  Proyecto académico desarrollado para la <b>Unidad 3</b> con el enfoque clásico de Java Web.
  <br />
  Conecta una aplicación JSP/Servlet con Microsoft SQL Server mediante JDBC sin dependencias de JavaScript.
</p>

</div>

---

## 📌 Descripción del Proyecto

Aplicación web para la administración del inventario de una **concesionaria de autos deportivos**. Permite visualizar el catálogo en tiempo real con fotografías, marcas y precios, registrar nuevos automóviles, actualizar información existente y eliminar vehículos con persistencia directa en **Microsoft SQL Server**.

> [!NOTE]
> Este proyecto utiliza exclusivamente **HTML5, Vanilla CSS, JSP (JavaServer Pages) y JSTL**, sin librerías externas de JavaScript en el cliente, respetando estrictamente el enfoque clásico de desarrollo web.

---

## 🛠️ Tecnologías y Versiones

| Componente | Tecnología | Versión | Propósito |
| :--- | :--- | :---: | :--- |
| **Lenguaje** | ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white) | `17 LTS` | Lógica del servidor y POJOs |
| **Servlets** | ![Jakarta](https://img.shields.io/badge/Jakarta%20Servlet-2C2255?style=flat-square) | `6.0.0` | Controlador HTTP (`doGet` y `doPost`) |
| **Vistas** | ![JSTL](https://img.shields.io/badge/JSTL-007396?style=flat-square) | `3.0.1` | Renderizado dinámico de tablas y condiciones |
| **Base de Datos** | ![SQL Server](https://img.shields.io/badge/SQL%20Server-CC292B?style=flat-square&logo=microsoftsqlserver&logoColor=white) | `2022` | Almacenamiento relacional |
| **Driver JDBC** | ![MSSQL Driver](https://img.shields.io/badge/mssql--jdbc-0078D4?style=flat-square) | `13.2.0.jre11` | Conexión Java a SQL Server |
| **Servidor** | ![Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?style=flat-square&logo=apachetomcat&logoColor=black) | `10.1` | Contenedor de Servlets local |

---

## 📋 Modelo de Datos (`Vehiculos`)

La base de datos almacena los vehículos con la siguiente estructura:

| Campo | Tipo SQL | Restricción | Descripción |
| :--- | :--- | :---: | :--- |
| `id` | `INT` | `PRIMARY KEY IDENTITY(1,1)` | Clave única generada automáticamente |
| `marca` | `NVARCHAR(50)` | `NOT NULL` | Fabricante (Ford, Porsche, BMW, Audi, Ferrari, etc.) |
| `modelo` | `NVARCHAR(50)` | `NOT NULL` | Versión específica del automóvil |
| `foto` | `NVARCHAR(500)` | `NOT NULL` | Enlace URL directo a la imagen del vehículo |
| `precio` | `INT` | `NOT NULL` | Precio en moneda nacional (MXN) |

---

## ⚙️ Rutas y Métodos HTTP

| Método | Endpoint | Parámetros | Acción Realizada |
| :---: | :--- | :--- | :--- |
| `GET` | `/SvVehiculo` | Ninguno | Consulta todos los vehículos y renderiza la vista principal |
| `GET` | `/SvVehiculo` | `accion=editar&id=X` | Consulta el vehículo `X` y lo precarga en el formulario |
| `POST` | `/SvVehiculo` | `accion=guardar` | Inserta un nuevo vehículo en la tabla `Vehiculos` |
| `POST` | `/SvVehiculo` | `accion=modificar` | Actualiza los datos del vehículo existente |
| `POST` | `/SvVehiculo` | `accion=eliminar` | Elimina el vehículo seleccionado de la base de datos |

---

## 🗄️ Script de Base de Datos (SQL Server)

Ejecuta el siguiente bloque en **SQL Server Management Studio (SSMS)**:

```sql
-- 1. Crear Base de Datos
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'Concesionaria')
BEGIN
    CREATE DATABASE Concesionaria;
END
GO

USE Concesionaria;
GO

-- 2. Crear Tabla de Vehiculos
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Vehiculos')
BEGIN
    CREATE TABLE Vehiculos (
        id INT IDENTITY(1,1) PRIMARY KEY,
        marca NVARCHAR(50) NOT NULL,
        modelo NVARCHAR(50) NOT NULL,
        foto NVARCHAR(500) NOT NULL,
        precio INT NOT NULL
    );
END
GO

-- 3. Crear Login y Usuario Propio de la Concesionaria
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'AdminAutos')
BEGIN
    CREATE LOGIN AdminAutos WITH PASSWORD = 'Autos123', CHECK_POLICY = OFF;
END
GO

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

-- 4. Registros Iniciales de Prueba
INSERT INTO Vehiculos (marca, modelo, foto, precio) VALUES
('Ford', 'Mustang GT Premium', 'https://images.unsplash.com/photo-1584345604476-8ec5e12e42dd?w=600&auto=format&fit=crop&q=80', 895000),
('Porsche', '911 Carrera S', 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=600&auto=format&fit=crop&q=80', 2150000),
('BMW', 'M4 Competition Coupe', 'https://images.unsplash.com/photo-1555215695-3004980ad54e?w=600&auto=format&fit=crop&q=80', 1650000),
('Audi', 'R8 V10 Performance', 'https://images.unsplash.com/photo-1603584173870-7f23fdae1b7a?w=600&auto=format&fit=crop&q=80', 2890000);
GO
```

---

## 📂 Estructura de Archivos

```text
ProyectoUnidad3/
├── .gitignore                    <- Archivos excluidos de control de versiones
├── pom.xml                       <- Dependencias de Maven (mssql-jdbc, JSTL, Servlets)
├── README.md                     <- Documentacion visual del proyecto
└── src/
    └── main/
        ├── java/
        │   ├── models/
        │   │   └── Vehiculo.java     <- Clase modelo (POJO con atributos y getters)
        │   └── servlets/
        │       └── SvVehiculo.java   <- Controlador con metodos doGet y doPost
        └── webapp/
            ├── index.jsp             <- Vista principal con JSTL (<c:forEach>, <c:if>)
            ├── style.css             <- Diseño deportivo (negro y rojo)
            └── WEB-INF/
                └── web.xml           <- Descriptor de despliegue en Tomcat
```

---

## 🚀 Guía de Ejecución en Apache NetBeans

1. Abre **Apache NetBeans**.
2. Selecciona **File > Open Project** y abre la carpeta `ProyectoUnidad3`.
3. Haz clic derecho sobre el proyecto y selecciona **Clean and Build**.
4. Haz clic derecho y selecciona **Run** (eligiendo tu servidor local **Apache Tomcat 10.1**).
5. Abre en tu navegador la dirección:
   ```text
   http://localhost:8080/ProyectoUnidad3/
   ```

> [!TIP]
> Si la lista de autos no aparece en la primera carga, la página redirige automáticamente al servlet `/SvVehiculo` para consultar y poblar la tabla con los datos de SQL Server.

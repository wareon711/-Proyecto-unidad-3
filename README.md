# Concesionaria Deportiva - Proyecto Unidad 3

Aplicación web desarrollada con el enfoque clásico de Java Web utilizando Servlets (Jakarta EE), JavaServer Pages (JSP), JSTL y conexión JDBC a Microsoft SQL Server.

---

### Tecnologias Utilizadas

| Tecnologia | Descripcion | Version |
| :--- | :--- | :--- |
| **Java** | Lenguaje de desarrollo backend | 17 (LTS) |
| **Jakarta Servlet** | API de servlets para procesamiento HTTP | 6.0.0 |
| **JSTL** | Biblioteca estándar de etiquetas para vistas JSP | 3.0.1 |
| **Microsoft SQL Server** | Sistema gestor de base de datos relacional | 2022 |
| **MSSQL JDBC Driver** | Conector oficial de Microsoft para JDBC | 13.2.0.jre11 |
| **Apache Tomcat** | Servidor web y contenedor de servlets | 10.1 |
| **Apache Maven** | Gestión de dependencias y construcción | 3.x |

---

### Tematica y Modelo de Datos

La aplicación implementa el catálogo de una concesionaria de autos deportivos con los siguientes atributos:

| Campo | Tipo SQL | Descripcion |
| :--- | :--- | :--- |
| `id` | `INT IDENTITY` | Identificador secuencial y clave primaria |
| `marca` | `NVARCHAR(50)` | Marca del vehiculo (Porsche, Ferrari, BMW, etc.) |
| `modelo` | `NVARCHAR(50)` | Nombre del modelo o version deportiva |
| `foto` | `NVARCHAR(500)` | Enlace web hacia la imagen del vehiculo |
| `precio` | `INT` | Precio del automovil en moneda nacional (MXN) |

---

### Funcionalidades

- **Lectura dinamica (GET):** Consulta todos los autos registrados en la base de datos y los renderiza en la tabla mediante JSTL (`<c:forEach>`).
- **Registro de vehiculos (POST):** Formulario para ingresar nuevos modelos mediante `PreparedStatement` seguro.
- **Edicion de registros (GET + POST):** Carga los datos de un vehículo en el formulario para modificarlos y guardarlos en base de datos.
- **Eliminacion (POST):** Borrado de registros seleccionados desde la tabla.
- **Numeracion consecutiva:** La columna de identificador se muestra siempre en orden correlativo (`#1, #2, #3...`) independientemente de los IDs eliminados.
- **Diseño propio:** Interfaz estilizada en tonalidades oscuras con acentos en rojo deportivo, responsive y sin dependencias externas de JavaScript.

---

### Configuracion de la Base de Datos

Ejecuta el siguiente script en **SQL Server Management Studio (SSMS)** conectado a tu instancia local:

```sql
CREATE DATABASE Concesionaria;
GO

USE Concesionaria;
GO

CREATE TABLE Vehiculos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    marca NVARCHAR(50) NOT NULL,
    modelo NVARCHAR(50) NOT NULL,
    foto NVARCHAR(500) NOT NULL,
    precio INT NOT NULL
);
GO

CREATE LOGIN AdminAutos WITH PASSWORD = 'Autos123', CHECK_POLICY = OFF;
GO

USE Concesionaria;
GO

CREATE USER AdminAutos FOR LOGIN AdminAutos;
GO

ALTER ROLE db_datareader ADD MEMBER AdminAutos;
ALTER ROLE db_datawriter ADD MEMBER AdminAutos;
GO

INSERT INTO Vehiculos (marca, modelo, foto, precio) VALUES
('Ford', 'Mustang GT', 'https://images.unsplash.com/photo-1584345604476-8ec5e12e42dd?w=600&auto=format&fit=crop&q=80', 895000),
('Porsche', '911 Carrera', 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=600&auto=format&fit=crop&q=80', 2150000),
('BMW', 'M4 Competition', 'https://images.unsplash.com/photo-1555215695-3004980ad54e?w=600&auto=format&fit=crop&q=80', 1650000),
('Audi', 'R8 V10', 'https://images.unsplash.com/photo-1603584173870-7f23fdae1b7a?w=600&auto=format&fit=crop&q=80', 2890000);
GO
```

---

### Estructura del Proyecto

```text
ProyectoUnidad3/
├── .gitignore
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   ├── models/
        │   │   └── Vehiculo.java
        │   └── servlets/
        │       └── SvVehiculo.java
        └── webapp/
            ├── index.jsp
            ├── style.css
            └── WEB-INF/
                └── web.xml
```

---

### Controladores y Rutas

| Metodo | Ruta | Descripcion |
| :--- | :--- | :--- |
| `GET` | `/SvVehiculo` | Consulta todos los registros y renderiza `index.jsp` |
| `GET` | `/SvVehiculo?accion=editar&id=X` | Obtiene los datos del vehiculo X y los envia al formulario |
| `POST` | `/SvVehiculo` (`guardar`) | Inserta un nuevo automovil en SQL Server |
| `POST` | `/SvVehiculo` (`modificar`) | Actualiza los datos del automovil indicado |
| `POST` | `/SvVehiculo` (`eliminar`) | Elimina el automovil seleccionado de la base de datos |

---

### Instrucciones de Ejecucion en NetBeans

1. Abre **Apache NetBeans**.
2. Ve a **File > Open Project** y selecciona la carpeta `ProyectoUnidad3`.
3. Haz clic derecho sobre el proyecto y presiona **Clean and Build**.
4. Haz clic derecho sobre el proyecto y selecciona **Run**.
5. NetBeans desplegará la aplicación en Apache Tomcat y abrirá en el navegador la dirección:
   ```text
   http://localhost:8080/ProyectoUnidad3/
   ```

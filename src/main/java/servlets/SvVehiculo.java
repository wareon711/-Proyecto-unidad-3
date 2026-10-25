package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import models.Vehiculo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "SvVehiculo", urlPatterns = {"/SvVehiculo"})
public class SvVehiculo extends HttpServlet {

    List<Vehiculo> lista = new ArrayList<>();
    String url = "jdbc:sqlserver://localhost:1433;databaseName=Concesionaria;encrypt=true;trustServerCertificate=true;characterEncoding=UTF-8";
    String usuario = "AdminAutos";
    String clave = "Autos123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");
        String id = request.getParameter("id");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conexion = DriverManager.getConnection(url, usuario, clave);

            if ("eliminar".equals(accion) && id != null) {
                PreparedStatement comando = conexion.prepareStatement("DELETE FROM Vehiculos WHERE id = ?");
                comando.setInt(1, Integer.parseInt(id));
                comando.executeUpdate();
            }

            if ("editar".equals(accion) && id != null) {
                PreparedStatement comando = conexion.prepareStatement("SELECT * FROM Vehiculos WHERE id = ?");
                comando.setInt(1, Integer.parseInt(id));
                ResultSet datos = comando.executeQuery();
                if (datos.next()) {
                    Vehiculo auto = new Vehiculo(
                            datos.getInt("id"),
                            datos.getString("marca"),
                            datos.getString("modelo"),
                            datos.getString("foto"),
                            datos.getInt("precio")
                    );
                    request.setAttribute("vehiculoEditar", auto);
                }
            }

            Statement consulta = conexion.createStatement();
            ResultSet datos = consulta.executeQuery("SELECT * FROM Vehiculos");

            lista.clear();
            while (datos.next()) {
                Vehiculo auto = new Vehiculo(
                        datos.getInt("id"),
                        datos.getString("marca"),
                        datos.getString("modelo"),
                        datos.getString("foto"),
                        datos.getInt("precio")
                );
                lista.add(auto);
            }

            conexion.close();

            request.setAttribute("lista", lista);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error en base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");
        String id = request.getParameter("id");
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        String foto = request.getParameter("foto");
        String precio = request.getParameter("precio");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conexion = DriverManager.getConnection(url, usuario, clave);

            if ("modificar".equals(accion) && id != null) {
                PreparedStatement comando = conexion.prepareStatement("UPDATE Vehiculos SET marca=?, modelo=?, foto=?, precio=? WHERE id=?");
                comando.setString(1, marca);
                comando.setString(2, modelo);
                comando.setString(3, foto);
                comando.setInt(4, Integer.parseInt(precio));
                comando.setInt(5, Integer.parseInt(id));
                comando.executeUpdate();
            } else if ("eliminar".equals(accion) && id != null) {
                PreparedStatement comando = conexion.prepareStatement("DELETE FROM Vehiculos WHERE id=?");
                comando.setInt(1, Integer.parseInt(id));
                comando.executeUpdate();
            } else {
                PreparedStatement comando = conexion.prepareStatement("INSERT INTO Vehiculos(marca, modelo, foto, precio) VALUES(?, ?, ?, ?)");
                comando.setString(1, marca);
                comando.setString(2, modelo);
                comando.setString(3, foto);
                comando.setInt(4, Integer.parseInt(precio));
                comando.executeUpdate();
            }

            conexion.close();
            doGet(request, response);

        } catch (Exception e) {
            throw new ServletException("Error al guardar vehiculo", e);
        }
    }
}

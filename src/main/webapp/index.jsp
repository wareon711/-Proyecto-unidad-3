<%@page import="models.Vehiculo"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${empty lista}">
    <c:redirect url="SvVehiculo"/>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Concesionaria Deportiva</title>
        <link rel="stylesheet" type="text/css" href="./style.css"/>
    </head>
    <body>
        <div class="contenedor">
            
            <div class="header">
                <h1>Concesionaria Deportiva</h1>
                <p class="subtitulo">Catalogo de vehiculos de alto rendimiento</p>
            </div>

            <div class="contenido">
                
                <div class="panel-formulario">
                    <div class="titulo-seccion">
                        <c:choose>
                            <c:when test="${not empty vehiculoEditar}">
                                <h2>Modificar Auto</h2>
                            </c:when>
                            <c:otherwise>
                                <h2>Registrar Auto</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <form action="SvVehiculo" method="POST" class="formulario">
                        <c:choose>
                            <c:when test="${not empty vehiculoEditar}">
                                <input type="hidden" name="accion" value="modificar"/>
                                <input type="hidden" name="id" value="${vehiculoEditar.getId()}"/>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="accion" value="guardar"/>
                            </c:otherwise>
                        </c:choose>

                        <div class="campo">
                            <label>Marca</label>
                            <input name="marca" type="text" placeholder="Ej. Porsche" value="${not empty vehiculoEditar ? vehiculoEditar.getMarca() : ''}" required />
                        </div>

                        <div class="campo">
                            <label>Modelo</label>
                            <input name="modelo" type="text" placeholder="Ej. 911 GT3 RS" value="${not empty vehiculoEditar ? vehiculoEditar.getModelo() : ''}" required />
                        </div>

                        <div class="campo">
                            <label>Precio (MXN)</label>
                            <input name="precio" type="number" min="0" placeholder="Ej. 2450000" value="${not empty vehiculoEditar ? vehiculoEditar.getPrecio() : ''}" required />
                        </div>

                        <div class="campo">
                            <label>Enlace de Foto</label>
                            <input name="foto" type="text" placeholder="https://..." value="${not empty vehiculoEditar ? vehiculoEditar.getFoto() : ''}" required />
                        </div>

                        <c:choose>
                            <c:when test="${not empty vehiculoEditar}">
                                <button type="submit" class="btn-guardar">Guardar Cambios</button>
                                <a href="SvVehiculo" class="btn-cancelar">Cancelar</a>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="btn-guardar">Guardar Vehiculo</button>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>

                <div class="panel-tabla">
                    <div class="barra-tabla">
                        <h2>Inventario Disponible</h2>
                        <form method="GET" action="SvVehiculo">
                            <button type="submit" class="btn-actualizar">Actualizar Tabla</button>
                        </form>
                    </div>

                    <c:if test="${not empty lista}">
                        <div class="tabla-contenedor">
                            <table class="tabla-autos">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Foto</th>
                                        <th>Marca</th>
                                        <th>Modelo</th>
                                        <th>Precio</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="veh" items="${lista}" varStatus="status">
                                        <tr>
                                            <td class="col-id">#${status.count}</td>
                                            <td class="col-foto">
                                                <img src="${veh.getFoto()}" alt="${veh.getModelo()}" onerror="this.src='https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=600&auto=format&fit=crop&q=80';"/>
                                            </td>
                                            <td class="col-marca">
                                                <span class="badge-marca">${veh.getMarca()}</span>
                                            </td>
                                            <td class="col-modelo">${veh.getModelo()}</td>
                                            <td class="col-precio">$${veh.getPrecio()} MXN</td>
                                            <td class="col-acciones">
                                                <a href="SvVehiculo?accion=editar&id=${veh.getId()}" class="btn-editar">Editar</a>
                                                <form action="SvVehiculo" method="POST" class="form-eliminar">
                                                    <input type="hidden" name="accion" value="eliminar"/>
                                                    <input type="hidden" name="id" value="${veh.getId()}"/>
                                                    <button type="submit" class="btn-eliminar">Eliminar</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${empty lista}">
                        <div class="mensaje-vacio">
                            <p>No hay autos registrados actualmente.</p>
                        </div>
                    </c:if>
                </div>

            </div>
        </div>
    </body>
</html>

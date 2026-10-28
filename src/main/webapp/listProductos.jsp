<%-- 
    Document   : listProductos
    Created on : 20/04/2025, 8:11:08 p. m.
    Author     : Asus 1
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.*"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Productos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body class="container mt-4">
        <h2>Productos Registrados</h2>
        <!-- Búsqueda por código -->
        <form class="mb-3" method="get" action="listProductos.jsp">
            <div class="input-group">
                <input type="text" name="codigo" value="<%= request.getParameter("codigo") != null ? request.getParameter("codigo") : ""%>" class="form-control" placeholder="Buscar código...">
                <button class="btn btn-primary" type="submit">Buscar</button>
            </div>
        </form>
        <a href="adminProductos.jsp" class="btn btn-primary mb-3">Agregar Producto</a>
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
                <tr><th>Código</th><th>Nombre</th><th>Cantidad</th><th>Precio</th><th>Imagen</th><th>Acciones</th></tr>
            </thead>
            <tbody>
                <%
                    GestionarProductos gestor = new GestionarProductos(getServletContext().getRealPath("/data/productos.txt"));
                    ArrayList<Producto> lista = gestor.getProductos();
                    String codB = request.getParameter("codigo");
                    if (codB != null && !codB.isEmpty()) {
                        lista.removeIf(p -> !p.getCodigo().contains(codB));
                    }
                    for (Producto p : lista) {
                %>
                <tr>
                    <td><%=p.getCodigo()%></td>
                    <td><%=p.getNombre()%></td>
                    <td><%=p.getCantidad()%></td>
                    <td><%=p.getPrecio()%></td>
                    <td><img src="<%=p.getImagen()%>" width="80"></td>
                    <td>
                        <form action="productos" method="post" style="display:inline;"><input type="hidden" name="accion" value="eliminar"><input type="hidden" name="codigo" value="<%=p.getCodigo()%>"><button onclick="return confirm('Eliminar?')" class="btn btn-danger btn-sm">Eliminar</button></form>
                        <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editar<%=p.getCodigo()%>">Editar</button>
                        <!-- Modal editar -->
                        <div class="modal fade" id="editar<%=p.getCodigo()%>" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog"><div class="modal-content">
                                    <form action="productos" method="post" enctype="multipart/form-data">
                                        <div class="modal-header"><h5 class="modal-title">Editar</h5><button class="btn-close" data-bs-dismiss="modal"></button></div>
                                        <div class="modal-body">
                                            <input type="hidden" name="accion" value="editar">
                                            <input type="hidden" name="codigo" value="<%=p.getCodigo()%>">
                                            <div class="mb-3"><label>Nombre</label><input class="form-control" name="nombre" value="<%=p.getNombre()%>"></div>
                                            <div class="mb-3"><label>Cantidad</label><input type="number" class="form-control" name="cantidad" value="<%=p.getCantidad()%>"></div>
                                            <div class="mb-3"><label>Precio</label><input type="number" step="0.01" class="form-control" name="precio" value="<%=p.getPrecio()%>"></div>
                                            <div class="mb-3"><label>Imagen nueva</label><input type="file" class="form-control" name="imagen"  ></div>
                                            <p>Actual: <img src="<%=p.getImagen()%>" width="100"></p>
                                        </div>
                                        <div class="modal-footer"><button class="btn btn-primary">Guardar</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button></div>
                                    </form>
                                </div></div>
                        </div>
                    </td>
                </tr>
                <% }%>
            </tbody>
        </table>
    </body>
</html>
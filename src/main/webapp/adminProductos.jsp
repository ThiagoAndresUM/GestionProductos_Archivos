<%-- 
    Document   : adminProductos
    Created on : 20/04/2025, 8:10:48 p. m.
    Author     : Asus 1
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
--%>

<!-- adminProductos.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Agregar Producto</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container mt-4">
        <h2>Agregar Producto</h2>
        <form action="productos" method="post" enctype="multipart/form-data">
            <input type="hidden" name="accion" value="crear">
            <div class="mb-3">
                <label>Código</label>
                <input type="text" name="codigo" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Nombre</label>
                <input type="text" name="nombre" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Cantidad</label>
                <input type="number" name="cantidad" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Precio</label>
                <input type="number" step="0.01" name="precio" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Imagen</label>
                <input type="file" name="imagen" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-success">Guardar</button>
            <a href="listProductos.jsp" class="btn btn-secondary">Ver Productos</a>
        </form>
        <% if ("true".equals(request.getParameter("success"))) { %>
        <div class="alert alert-success mt-3">Producto agregado.</div>
        <% } else if ("duplicado".equals(request.getParameter("error"))) { %>
        <div class="alert alert-danger mt-3">Código duplicado.</div>
        <% }%>
    </body>
</html>
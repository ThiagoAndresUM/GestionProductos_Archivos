/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Modelo.GestionarProductos;
import Modelo.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;

/**
 *
 * @author Asus 1
 */
@WebServlet("/productos")
@MultipartConfig
public class ServletProductos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletProductos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletProductos at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String accion = request.getParameter("accion");

        // Ruta dentro de webapp/data/productos.txt
        String rutaDatos = getServletContext().getRealPath("/data/productos.txt");
        GestionarProductos gestor = new GestionarProductos(rutaDatos);

        String codigo = request.getParameter("codigo");

        if ("crear".equals(accion) || "editar".equals(accion)) {
            String nombre = request.getParameter("nombre");
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            double precio = Double.parseDouble(request.getParameter("precio"));
            Part archivoImagen = request.getPart("imagen");

            // Validar duplicados al crear
            if ("crear".equals(accion) && gestor.buscar(codigo) != null) {
                response.sendRedirect("adminProductos.jsp?error=duplicado");
                return;
            }

            // Guardar imagen solo si es nueva
            String nombreArchivo = archivoImagen.getSubmittedFileName();
            String rutaImagenRel;

            if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                rutaImagenRel = "images/" + nombreArchivo;
                String rutaImagenAbs = getServletContext().getRealPath(rutaImagenRel);
                File carpetaImg = new File(rutaImagenAbs).getParentFile();
                if (!carpetaImg.exists()) {
                    carpetaImg.mkdirs();
                }
                archivoImagen.write(rutaImagenAbs);
            } else {
                Producto existente = gestor.buscar(codigo);
                rutaImagenRel = (existente != null) ? existente.getImagen() : "";
            }

            Producto producto = new Producto(codigo, nombre, cantidad, precio, rutaImagenRel);

            if ("crear".equals(accion)) {
                gestor.agregar(producto);
                response.sendRedirect("adminProductos.jsp?success=true");
                return;
            } else if ("editar".equals(accion)) {
                gestor.actualizar(producto);
                response.sendRedirect("listProductos.jsp?success=editado");
                return;
            }
        } else if ("eliminar".equals(accion)) {
            gestor.eliminar(codigo);
            response.sendRedirect("listProductos.jsp?success=eliminado");
            return;
        }

        response.sendRedirect("listProductos.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

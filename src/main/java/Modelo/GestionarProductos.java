/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Asus 1
 */
public class GestionarProductos {

    private final String rutaArchivo;
    private ArrayList<Producto> productos;

    /**
     * Constructor que recibe la ruta absoluta del archivo de texto.
     */
    public GestionarProductos(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.productos = leerArchivo();
    }

    /**
     * Agrega un nuevo producto y actualiza el archivo de texto.
     */
    public void agregar(Producto p) {
        productos.add(p);
        guardarArchivo();
    }

    /**
     * Actualiza un producto existente y reescribe el archivo de texto.
     */
    public void actualizar(Producto p) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(p.getCodigo())) {
                productos.set(i, p);
                break;
            }
        }
        guardarArchivo();
    }

    /**
     * Elimina un producto por código y reescribe el archivo.
     */
    public void eliminar(String codigo) {
        productos.removeIf(prod -> prod.getCodigo().equals(codigo));
        guardarArchivo();
    }

    /**
     * Devuelve la lista de productos.
     */
    public ArrayList<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    /**
     * Busca un producto por código.
     */
    public Producto buscar(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Guarda todos los productos en el archivo de texto, creando la carpeta
     * "data" en webapp si es necesario.
     */
    private void guardarArchivo() {
        File archivo = new File(rutaArchivo);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Producto p : productos) {
                bw.write(String.join(",",
                        p.getCodigo(),
                        p.getNombre(),
                        String.valueOf(p.getCantidad()),
                        String.valueOf(p.getPrecio()),
                        p.getImagen()
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee el archivo de texto y devuelve la lista de productos.
     */
    private ArrayList<Producto> leerArchivo() {
        File archivo = new File(rutaArchivo);
        ArrayList<Producto> lista = new ArrayList<>();
        if (!archivo.exists()) {
            return lista;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 5) {
                    Producto p = new Producto(
                            campos[0],
                            campos[1],
                            Integer.parseInt(campos[2]),
                            Double.parseDouble(campos[3]),
                            campos[4]
                    );
                    lista.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

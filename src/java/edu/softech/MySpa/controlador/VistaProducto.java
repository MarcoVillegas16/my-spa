package edu.softech.MySpa.controlador;

// Importes necesarios
import com.google.gson.Gson;
import edu.softech.MySpa.baseDatos.comandos.comandosProducto;
import edu.softech.MySpa.modelo.Producto;
import edu.softech.MySpa.modelo.Producto_Sucursal;
import edu.softech.MySpa.modelo.Sucursal;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author logic
 */
@Path("producto")
public class VistaProducto extends Application {

    // Objeto necesario para manipular objetos Producto.
    Producto producto;
    
    // Objeto necesario para manipular la relación Producto - Sucursal.
    Producto_Sucursal producto_sucursal;
    
    // Objeto necesario para manipular objetos Sucursal.
    Sucursal sucursal;

    // Objeto controlador para los comandos Producto
    comandosProducto cmProductos = new comandosProducto();

    // Respuesta JSON
    String respuesta;

    /**
     * Busca y devuelve los datos del producto (producto - sucursal)
     * cuyo idProducto se ingrese. 
     * @param idProducto del producto
     * @return JSON con los datos del producto
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProducto(@QueryParam("idProducto") String idProducto) {

        // Crea un objeto Producto y le empata su atributo idProducto
        producto = new Producto();
        producto.setIdProducto(Integer.parseInt(idProducto));
        
        // Crea un objeto Sucursal, necesario para el objeto Producto_Sucursal
        sucursal = new Sucursal();
        
        // Crea un objeto Producto_Sucursal, necesario para la busqueda.
        // Stock se declara como 0 para eviar conflictos con el constructor.
        producto_sucursal = new Producto_Sucursal(sucursal, producto, 0);

        try {
            // Manda el objeto y la opcion a evaluar, y crea un JSON de la respuesta
            respuesta = new Gson().toJson(cmProductos.buscarProducto(producto_sucursal));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    /**
     * Devuleve todos los productos.
     * @return JSON con objetos Producto_Sucursal
     */
    @GET
    @Path("listado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductos() {

        // Obtiene todos los productos de la base de datos, y crea un JSON con ellos
        try {
            respuesta = new Gson().toJson(cmProductos.buscarProductos());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }

    /**
     * Inserta un nuevo registro a la base de datos.
     * @param nombre del producto.
     * @param marca del producto.
     * @param precioUso del producto.
     * @param idSucursal de la sucursal.
     * @param stock (cantidad) del producyo.
     * @return Un JSON con el objeto creado.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearProducto(@QueryParam("nombre") String nombre,
            @QueryParam("marca") String marca,
            @QueryParam("precioUso") String precioUso,
            @QueryParam("idSucursal") String idSucursal,
            @QueryParam("stock") String stock) {

        // Se crea un producto al cual se le empataran 3 de sus 5 atributos (nombre, marca y precioUso)
        producto = new Producto();
        producto.setNombre(nombre);
        producto.setMarca(marca);
        producto.setPrecioUso(Float.parseFloat(precioUso));
        
        // Se crea un objeto sucursal el cual empatara su atributo idSucursal.
        sucursal = new Sucursal();
        sucursal.setIdSucursal(Integer.parseInt(idSucursal));
        
        // Se crea un objeto que relaciona producto - sucursal, el cual tambien
        // contendra el stock
        producto_sucursal = new Producto_Sucursal(sucursal,
                producto,
                Integer.parseInt(stock));

        try {
            // Registra el producto y crea un JSON de la respuesta de la BD.
            respuesta = new Gson().toJson(cmProductos.registarProducto(producto_sucursal));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    /**
     * Actualiza un registro Producto determinado.
     * @param idProducto
     * @param nombre
     * @param marca
     * @param precioUso
     * @param idSucursal
     * @param stock
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarProducto(@QueryParam("idProducto") String idProducto,
            @QueryParam("nombre") String nombre,
            @QueryParam("marca") String marca,
            @QueryParam("precioUso") String precioUso,
            @QueryParam("idSucursal") String idSucursal,
            @QueryParam("stock") String stock) {

        // Se crea el producto y se le dan sus atributos
        producto = new Producto();
        producto.setIdProducto(Integer.parseInt(idProducto));
        producto.setNombre(nombre);
        producto.setMarca(marca);
        producto.setPrecioUso(Float.parseFloat(precioUso));
        
        // Se crea un objeto Sucursal con la nueva idSucursal.
        sucursal = new Sucursal();
        sucursal.setIdSucursal(Integer.parseInt(idSucursal));
        
        // Se crea el objeto Producto_Sucursal que contnedra a Producto
        // y Sucursal, y que sera evaludado.
        // Stock se declara como 0 para eviar conflictos con el constructor.
        producto_sucursal = new Producto_Sucursal(sucursal,
                producto,
                Integer.parseInt(stock));

        try {
            // Manda @producto a evaluar, y crea un JSON de la respuesta
            if (cmProductos.modificarProducto(producto_sucursal)) {
                respuesta = new Gson().toJson(producto_sucursal);
            } else {
                respuesta = null;
                respuesta = new Gson().toJson(respuesta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    /**
     * Cambia el atributo @estatus de un registro Producto a '2' (eliminado)
     * en la base de datos para indicar que esta fuera de servicio.
     * @param idProducto
     * @return
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarProducto(@QueryParam("idProducto") String idProducto) {

        // Crea un objeto Producto al que se le empatara el idProducto
        producto = new Producto();
        producto.setIdProducto(Integer.parseInt(idProducto));
        
        // Crea un objeto Sucursal, necesario para la evaluación.
        sucursal = new Sucursal();
        
        // Crea un objeto Producto_Sucursal, necesario para la evaluación.
        // Stock se declara como 0 para eviar conflictos con el constructor.
        producto_sucursal = new Producto_Sucursal(sucursal, producto, 0);

        try {
            // Manda el objeto a evaluar y crea un JSON de la respuesta
            if(cmProductos.borrarProducto(producto_sucursal) != null) {
                respuesta = new Gson().toJson(cmProductos.borrarProducto(producto_sucursal));
            } else {
                respuesta = new Gson().toJson(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }
}

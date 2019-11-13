package edu.softech.MySpa.controlador;

import com.google.gson.Gson;
import edu.softech.MySpa.baseDatos.comandos.comandosProducto;
import edu.softech.MySpa.modelo.Producto;
import java.sql.SQLException;

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

    // Objeto necesario para manipular otros objeto Producto
    Producto producto;

    // Objeto controlador para los comandos Producto
    comandosProducto cmProductos = new comandosProducto();

    // Respuesta JSON
    String respuesta;

    /**
     *
     * @param idProducto
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProducto(@QueryParam("idProducto") String idProducto) {

        // Obtiene un registro producto de la base de datos, y crea un JSON con el.
        // Crea un objeto Producto y lo empata dependiendo de que criterio
        // se uso para buscalo.
        producto = new Producto();

        // Da el atributo idProducto al objeto Producto
        producto.setIdProducto(Integer.parseInt(idProducto));

        try {
            // Manda el objeto y la opcion a evaluar, y crea un JSON de la respuesta
            respuesta = new Gson().toJson(cmProductos.buscarProducto(producto));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    /**
     *
     * @return
     */
    @GET
    @Path("listado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProductos() {

        // Obtiene todos los productos de la base de datos, y crea un JSON con ellos
        try {
            respuesta = new Gson().toJson(cmProductos.buscarProductos());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }

    /**
     *
     * @param nombre
     * @param marca
     * @param precioUso
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearProducto(@QueryParam("nombre") String nombre,
            @QueryParam("marca") String marca,
            @QueryParam("precioUso") String precioUso) {

        // Inserta un nuevo registro a la base de datos. Para ello, crea
        // un nuevo objeto producto
        // Se crea un producto al cual se le empataran 3 de sus 5 atributos
        // Esto porque @idProducto y @estatus se autogeneraran en la base de datos
        producto = new Producto();
        producto.setNombre(nombre);
        producto.setMarca(marca);
        producto.setPrecioUso(Float.parseFloat(precioUso));

        try {
            // Manda @producto a evaluar, y crea un JSON de la respuesta
            respuesta = new Gson().toJson(cmProductos.registarProducto(producto));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    /**
     *
     * @param idProducto
     * @param nombre
     * @param marca
     * @param estatus
     * @param precioUso
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarProducto(@QueryParam("idProducto") String idProducto,
            @QueryParam("nombre") String nombre,
            @QueryParam("marca") String marca,
            @QueryParam("estatus") String estatus,
            @QueryParam("precioUso") String precioUso) {

        // Actualiza un registro Producto determinado.
        // Se crea el producto actualizado
        producto = new Producto(Integer.parseInt(idProducto), nombre, marca,
                Integer.parseInt(estatus), Float.parseFloat(precioUso));

        try {
            // Manda @producto a evaluar, y crea un JSON de la respuesta
            if(cmProductos.modificarProducto(producto))
                respuesta = new Gson().toJson(producto);
            else 
                respuesta = null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }
    
    /**
     * 
     * @param idProducto0
     * @return 
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarProducto(@QueryParam("idProducto0") String idProducto0) {

        /*
        Cambia el atributo @estatus de un registro Producto a '2' (eliminado)
        en la base de datos para indicar que esta fuera de servicio.
        Enviara un objeto Producto con el atributo @idProducto para buscar el
        registro con el mismo ID, y cambiar su estatus
         */
        
        // Crea un objeto Producto al que se le empatara el atributo @idProducto
        producto = new Producto();
        
        producto.setIdProducto(Integer.parseInt(idProducto0));

        try {

            // Manda el objeto a evaluar y crea un JSON de la respuesta
            respuesta = new Gson().toJson(cmProductos.borrarProducto(producto));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }
}

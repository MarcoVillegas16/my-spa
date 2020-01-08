/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.controlador;

import com.google.gson.Gson;
import edu.softech.MySpa.baseDatos.comandos.comandosTratamiento;
import edu.softech.MySpa.modelo.Tratamiento;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author logic
 */
@Path("tratamiento")
public class VistaTratamiento extends Application {

    // Objeto necesario para manipular objetos Tratamiento
    Tratamiento tratamiento;

    // Objeto controlador para los comandos Tratamiento
    comandosTratamiento cmTratamiento = new comandosTratamiento();

    // Respuesta JSON
    String respuesta;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProducto(@QueryParam("idTratamiento") String idTratamiento) {

        // Crea un objeto Tratamiento y le asigna un idTratamiento
        // Busca el registro cuyo @idTratamiento coincida con el del objeto
        // previamente creado.
        tratamiento = new Tratamiento();

        tratamiento.setIdTratamiento(Integer.parseInt(idTratamiento));

        try {
            // Manda el objeto a evaluar y crea un JSON de la respuesta
            respuesta = new Gson().toJson(cmTratamiento.buscarTratamiento(tratamiento));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }

    @GET
    @Path("listado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTratamientos() {

        // Obteiene todos los registros de 'v_tratamientos' y crea un JSON con ellos
        try {
            respuesta = new Gson().toJson(cmTratamiento.buscarTratamientos());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarTratamiento(@QueryParam("nombre") String nombre,
            @QueryParam("descripcion") String descripcion,
            @QueryParam("costo") String costo) {

        // Creación de un nuevo registro para la tabla Tratamiento
        /*
        Creamos un objeto Tratamiento con los atributos de @nombre,
        @descripcion y @costo. Los atributos @idTratamiento y @estatus
        se autoasignaran en la base de datos.
         */
        tratamiento = new Tratamiento();
        tratamiento.setNombre(nombre);
        tratamiento.setDescripcion(descripcion);
        tratamiento.setCosto(Float.parseFloat(costo));

        try {
            // Manda @tratamiento a evaluar, y crea un JSON de la respuesta
            respuesta = new Gson().toJson(cmTratamiento.registrarTratamiento(tratamiento));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarTratamiento(@QueryParam("idTratamiento") String idTratamiento,
            @QueryParam("nombre") String nombre,
            @QueryParam("descripcion") String descripcion,
            @QueryParam("costo") String costo) {
        
        // Se crea el producto y se le asignan sus atributos
        tratamiento = new Tratamiento();
        tratamiento.setIdTratamiento(Integer.parseInt(idTratamiento));
        tratamiento.setNombre(nombre);
        tratamiento.setDescripcion(descripcion);
        tratamiento.setCosto(Float.parseFloat(costo));

        try {
            // Manda @tratamiento a evaluar, y crea un JSON de la respuesta
            respuesta = (cmTratamiento.modificarTratamiento(tratamiento))
                    ? new Gson().toJson(tratamiento) :  new Gson().toJson(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarProducto(@QueryParam("idTratamiento") String idTratamiento) {

        /*
        Cambia el atributo @estatus de un registro Tratamiento a '2' (eliminado)
        en la base de datos para indicar que esta fuera de servicio.
        Enviara un objeto Tratamiento con el atributo @idTratamiento para buscar el
        registro con el mismo ID, y cambiar su estatus
        Si no lo encuentra, devuleve nulo.
         */
        // Crea el objeto y le asigna el atributo.
        tratamiento = new Tratamiento();
        tratamiento.setIdTratamiento(Integer.parseInt(idTratamiento));

        try {
            // Manda @tratamiento a evaluar, y crea un JSON de la respuesta
            respuesta = new Gson().toJson(cmTratamiento.borrarTratamiento(tratamiento));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna el JSON
        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

}

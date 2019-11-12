/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.controlador;

import com.google.gson.Gson;
import edu.softech.MySpa.baseDatos.comandos.ComandosEmpleado;
import edu.softech.MySpa.modelo.Empleado;
import edu.softech.MySpa.modelo.Usuario;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Esau
 */
@Path("empleado")
public class VistaEmpleado
{

    ComandosEmpleado comandos = new ComandosEmpleado();
    //ControladorEmpleado controlador = new ControladorEmpleado();

    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    public String ping()
    {
        return "encendido";
    }

    @GET
    @Path("/listado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar()
    {
        Status statusResponse = Response.Status.NO_CONTENT; // sirve para manejar los estatus del servidor
        String json = null;

        List<Empleado> content = null;
        try
        {
            content = comandos.read();
            statusResponse = Response.Status.OK;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            statusResponse = Response.Status.BAD_REQUEST;
        }
        finally
        {
            json = new Gson().toJson(content);
            return Response.status(statusResponse).entity(json).build();
        }
    }

    @GET
    @Path("/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(
            @QueryParam("numeroEmpleado") String numeroEmpleado,
            @QueryParam("nombreUsuario") String nombreUsuario)
    {
        Status statusResponse = Response.Status.NO_CONTENT; // sirve para manejar los estatus del servidor
        String json = null;

        Empleado content = new Empleado();
        content.setNumeroEmpleado(numeroEmpleado);
        content.getUsuario().setNombreUsuario(nombreUsuario);
        try
        {
            content = comandos.getByCredentials(content, 1);
            statusResponse = Response.Status.OK;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            statusResponse = Response.Status.BAD_REQUEST;
        }
        finally
        {
            json = new Gson().toJson(content);
            return Response.status(statusResponse).entity(json).build();
        }
    }

    /*
     * 7 / 11 / 2019 21:00 h
     *
     * Se modificó para no recibir id's
     */
    @POST
    @Path("/insertar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertar(
            /*
             * Empleado
             */
            @QueryParam("numeroEmpleado") String numeroEmpleado,
            @QueryParam("puesto") String puesto,
            @QueryParam("estatus") String estatus,
            @QueryParam("foto") String foto,
            @QueryParam("rutaFoto") String rutaFoto,
            /*
             * Usuario
             */
            @QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("contrasenia") String contrasenia,
            @QueryParam("rol") String rol,
            /*
             * Persona
             */
            @QueryParam("nombre") String nombre,
            @QueryParam("apellidoPaterno") String apellidoPaterno,
            @QueryParam("apellidoMaterno") String apellidoMaterno,
            @QueryParam("genero") String genero,
            @QueryParam("domicilio") String domicilio,
            @QueryParam("telefono") String telefono,
            @QueryParam("rfc") String rfc)
    {
        Status statusResponse = Response.Status.NO_CONTENT; // sirve para manejar los estatus del servidor
        String json = "{\"OK\":";

        Empleado content = new Empleado(
                0, // idEmpleado defecto
                numeroEmpleado,
                puesto,
                Integer.valueOf(estatus),
                foto,
                rutaFoto,
                new Usuario(
                        0, // idUsuario defecto
                        nombreUsuario,
                        contrasenia,
                        rfc),
                0, // idPersona defecto
                nombre,
                apellidoPaterno,
                apellidoMaterno,
                genero,
                domicilio,
                telefono,
                rfc);
        try
        {
            comandos.create(content);
            statusResponse = Response.Status.OK;
            json += "\"TRUE\"";
        }
        catch (Exception ex)
        {
            json += "\"FALSE\"";
            ex.printStackTrace();
            statusResponse = Response.Status.BAD_REQUEST;
        }
        finally
        {
            json += "}";
        }
        return Response.status(statusResponse).entity(json).build();
    }

    @PUT
    @Path("/actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(
            /*
             * Empleado
             */
            @QueryParam("idEmpleado") String idEmpleado,
            @QueryParam("numeroEmpleado") String numeroEmpleado,
            @QueryParam("puesto") String puesto,
            @QueryParam("estatus") String estatus,
            @QueryParam("foto") String foto,
            @QueryParam("rutaFoto") String rutaFoto,
            /*
             * Usuario
             */
            @QueryParam("idUsuario") String idUsuario,
            @QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("contrasenia") String contrasenia,
            @QueryParam("rol") String rol,
            /*
             * Persona
             */
            @QueryParam("idPersona") String idPersona,
            @QueryParam("nombre") String nombre,
            @QueryParam("apellidoPaterno") String apellidoPaterno,
            @QueryParam("apellidoMaterno") String apellidoMaterno,
            @QueryParam("genero") String genero,
            @QueryParam("domicilio") String domicilio,
            @QueryParam("telefono") String telefono,
            @QueryParam("rfc") String rfc)
    {
        Status statusResponse = Response.Status.NO_CONTENT; // sirve para manejar los estatus del servidor
        String json = "{\"OK\":";

        Empleado content = new Empleado(
                Integer.valueOf(idEmpleado),
                numeroEmpleado,
                puesto,
                Integer.valueOf(estatus),
                foto,
                rutaFoto,
                new Usuario(
                        Integer.valueOf(idUsuario),
                        nombreUsuario,
                        contrasenia,
                        rfc),
                Integer.valueOf(idPersona),
                nombre,
                apellidoPaterno,
                apellidoMaterno,
                genero,
                domicilio,
                telefono,
                rfc);
        try
        {
            comandos.update(content);
            statusResponse = Response.Status.OK;
            json += "\"TRUE\"";
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            statusResponse = Response.Status.BAD_REQUEST;
            json += "\"FALSE\"";
        }
        finally
        {
            json += "}";
        }

        return Response.status(statusResponse).entity(json).build();
    }

    @DELETE
    @Path("/eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@QueryParam("numeroEmpleado") String numeroEmpleado)
    {
        Status statusResponse = Response.Status.NO_CONTENT; // sirve para manejar los estatus del servidor
        String json = "{\"OK\":";

        try
        {
            comandos.deleteUnique(numeroEmpleado);
            statusResponse = Response.Status.OK;
            json += "\"TRUE\"";
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            statusResponse = Response.Status.BAD_REQUEST;
            json += "\"FALSE\"";
        }
        finally
        {
            json += "}";
        }

        return Response.status(statusResponse).entity(json).build();
    }
}

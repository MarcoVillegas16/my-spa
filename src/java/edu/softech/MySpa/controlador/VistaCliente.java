/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.controlador;

import com.google.gson.Gson;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.controlador.validarDatos.ControladorCliente;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author marco
 */
@Path("cliente")
public class VistaCliente extends Application {

    String respuesta;
    ControladorCliente cCliente = new ControladorCliente();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCliente(@QueryParam("nombre") String nombre,
            @QueryParam("apellidoPaterno") String apellidoPaterno,
            @QueryParam("apellidoMaterno") String apellidoMaterno,
            @QueryParam("genero") String genero,
            @QueryParam("telefono") String telefono,
            @QueryParam("rfc") String rfc,
            @QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("correo") String correo,
            @QueryParam("contrasenia") String contrasenia,
            @QueryParam("domicilio") String domicilio) {

        ArrayList<String> datos = new ArrayList<>();

        datos.add(nombre);
        datos.add(apellidoPaterno);
        datos.add(apellidoMaterno);
        datos.add(genero);
        datos.add(telefono);
        datos.add(rfc);
        datos.add(nombreUsuario);
        datos.add(correo);
        datos.add(contrasenia);
        datos.add(domicilio);

        Cliente c = cCliente.crearCliente(datos, 1);

        respuesta = new Gson().toJson(c);

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarCliente(
            @QueryParam("nombre") String nombre,
            @QueryParam("apellidoPaterno") String apellidoPaterno,
            @QueryParam("apellidoMaterno") String apellidoMaterno,
            @QueryParam("genero") String genero,
            @QueryParam("telefono") String telefono,
            @QueryParam("rfc") String rfc,
            @QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("correo") String correo,
            @QueryParam("contrasenia") String contrasenia,
            @QueryParam("domicilio") String domicilio,
            @QueryParam("numeroUnicoCliente") String numeroUnicoCliente) {

        ArrayList<String> datos = new ArrayList<>();

        datos.add(nombre);
        datos.add(apellidoPaterno);
        datos.add(apellidoMaterno);
        datos.add(genero);
        datos.add(telefono);
        datos.add(rfc);
        datos.add(nombreUsuario);
        datos.add(correo);
        datos.add(contrasenia);
        datos.add(domicilio);
        datos.add(numeroUnicoCliente);

        Cliente cliente = cCliente.crearCliente(datos, 2);

        respuesta = new Gson().toJson(cliente);

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarCliente(
            @QueryParam("numeroUnicoCliente") String numeroUnicoCliente,
            @QueryParam("nombreUsuario") String nombreUsuario) {

        ArrayList<String> datos = new ArrayList<>();

        datos.add(numeroUnicoCliente);
        datos.add(nombreUsuario);

        boolean respuestaServidor = false;
        try {
            respuestaServidor = cCliente.borrarCliente(datos);
        } catch (Exception ex) {
            Logger.getLogger(VistaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        respuesta = new Gson().toJson(respuestaServidor);

        return Response.status(Response.Status.OK).entity(respuesta).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarCliente(
            @QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("contrasenia") String contrasenia) {

        ArrayList<String> datos = new ArrayList<>();

        datos.add(nombreUsuario);
        datos.add(contrasenia);

        Cliente c = null;
        try {
            c = cCliente.buscarCliente(datos, 1);
            respuesta = new Gson().toJson(c);

        } catch (Exception ex) {
            Logger.getLogger(VistaCliente.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.OK).entity(null).build();
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();
    }

    @GET
    @Path("clientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarClientes() {

        Cliente c = null;
        try {

            respuesta = new Gson().toJson(cCliente.buscarClientes());

        } catch (Exception ex) {
            Logger.getLogger(VistaCliente.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.OK).entity(null).build();
        }

        return Response.status(Response.Status.OK).entity(respuesta).build();
    }

}

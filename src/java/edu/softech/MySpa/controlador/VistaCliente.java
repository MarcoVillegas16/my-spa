/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.controlador;

import com.google.gson.Gson;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.controlador.validarDatos.ControladorCliente;
import edu.softech.MySpa.modelo.Usuario;
import java.util.ArrayList;
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

        Cliente c = cCliente.crearCliente(datos);

        respuesta = new Gson().toJson(c);

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarCliente(            
            @QueryParam("numeroUnicoCliente") String numeroUnicoCliente,
            @QueryParam("nombre") String nombre,
            @QueryParam("apellidoPaterno") String apellidoPaterno,
            @QueryParam("apellidoMaterno") String apellidoMaterno,
            @QueryParam("genero") String genero,
            @QueryParam("telefono") String telefono,
            @QueryParam("rfc") String rfc,
            @QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("correo") String correo,
            @QueryParam("contrasenia") String contrasenia,
            @QueryParam("domicilio") String domicilio) {

        Usuario usuario = new Usuario(0, nombreUsuario, contrasenia, "cliente");

        Cliente cliente = new Cliente(0, correo, 1, 0,
                nombre, apellidoPaterno, apellidoMaterno, genero, domicilio,
                telefono, rfc, usuario);

        //respuesta = new Gson().toJson(cliente);
        respuesta = "Holaaaaaaa";

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.vista;

import com.google.gson.Gson;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Domicilio;
import edu.softech.MySpa.modelo.Usuario;
import javax.ws.rs.GET;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCliente(@QueryParam("nombre") String nombre,
            @QueryParam("apellidoPaterno") String apellidoPaterno,
            @QueryParam("apellidoMaterno") String apellidoMaterno,
            @QueryParam("genero") String genero,
            @QueryParam("telefonoCelular") String telefonoCelular,
            @QueryParam("telefonoCasa") String telefonoCasa,
            @QueryParam("rfc") String rfc,
            @QueryParam("nombreUsuario") String nombreUsuario,
            @QueryParam("contrasenia") String contrasenia,
            @QueryParam("calle") String calle,
            @QueryParam("numeroCasa") String numeroCasa,
            @QueryParam("colonia") String colonia) {

        Domicilio domicilio = new Domicilio(0, calle, numeroCasa, colonia);

        Usuario usuario = new Usuario(0, nombreUsuario, contrasenia, "cliente",
                "activo");

        Cliente cliente = new Cliente(0, nombreUsuario, 0, nombre, apellidoPaterno,
                apellidoMaterno, genero, telefonoCelular, telefonoCasa, rfc,
                usuario, domicilio);

        respuesta = new Gson().toJson(cliente);

        return Response.status(Response.Status.OK).entity(respuesta).build();

    }

}

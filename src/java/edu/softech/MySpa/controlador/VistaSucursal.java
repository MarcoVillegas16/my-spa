/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.controlador;

import edu.softech.MySpa.baseDatos.conexionBasesDatos;
import edu.softech.MySpa.baseDatos.comandos.ComandosSucursales;
import com.google.gson.Gson;
import edu.softech.MySpa.modelo.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sucursal")
public class VistaSucursal extends Application {

    String respuesta;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertarSucursal(@QueryParam("nombre") @DefaultValue("") String nombre, @QueryParam("domicilio") @DefaultValue("") String domicilio, @QueryParam("latitud") @DefaultValue("") String latitud, @QueryParam("longitud") @DefaultValue("") String longitud) throws Exception {

        conexionBasesDatos c = new conexionBasesDatos();
        Sucursal s = new Sucursal();
        s.setNombre(nombre);
        s.setDomicilio(domicilio);
        s.setEstatus(1);
        s.setLatitud(Double.parseDouble(latitud));
        s.setLongitud(Double.parseDouble(longitud));

        ComandosSucursales cs = new ComandosSucursales();

        if (verificarDatos(s) == null) {
            //NUEVA SUCURSAL
            String out = new Gson().toJson(cs.insertarSucursal(c, s));
            return Response.status(Response.Status.OK).entity(out).build();
        } else {
            //SI EXISTE LA SUCURSAL
            String out = "ERROR";
            return Response.status(Response.Status.OK).entity(out).build();
        }

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarSucursal(@QueryParam("idSucursal") @DefaultValue("") String idSucursal, @QueryParam("nombre") @DefaultValue("") String nombre, @QueryParam("domicilio") @DefaultValue("") String domicilio, @QueryParam("latitud") @DefaultValue("") String latitud, @QueryParam("longitud") @DefaultValue("") String longitud) throws Exception {
        conexionBasesDatos c = new conexionBasesDatos();
        Sucursal su = new Sucursal();
        ComandosSucursales cs = new ComandosSucursales();

        su.setIdSucursal(Integer.parseInt(idSucursal));
        su.setNombre(nombre);
        su.setDomicilio(domicilio);
        su.setLatitud(Double.parseDouble(latitud));
        su.setLongitud(Double.parseDouble(longitud));
        Sucursal sucursal = cs.obtenerSucursal(c, su);
        su.setIdSucursal(sucursal.getIdSucursal());

        if (verificarDatos(su) != null) {
            Sucursal encontrada = cs.obtenerSucursal(c, su);
            su.setIdSucursal(encontrada.getIdSucursal());

            String out = new Gson().toJson(cs.actualizarSucursal(c, su));

            return Response.status(Response.Status.OK).entity(out).build();
        } else {
            String out = "Sucursal no encontrada";
            return Response.status(Response.Status.OK).entity(out).build();
        }
    }

    @Path("listado")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSucursales() throws Exception {
        conexionBasesDatos c = new conexionBasesDatos();
        Sucursal sucursales[] = ComandosSucursales.obtenerLista(c);
        String sucursal = "";
        for (int i = 0; i < sucursales.length; i++) {
            sucursal += sucursales[i].getIdSucursal() + ", " + sucursales[i].getNombre() + ", " + sucursales[i].getDomicilio() + ", " + sucursales[i].getLatitud() + ", " + sucursales[i].getLongitud() + ", " + sucursales[i].getEstatus();
        }
        String out = new Gson().toJson(sucursales);

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarSucursal(@QueryParam("idSucursal") @DefaultValue("") String idSucursal) throws Exception {
        ComandosSucursales cs = new ComandosSucursales();
        Sucursal s = new Sucursal();
        s.setIdSucursal(Integer.parseInt(idSucursal));

        if (verificarDatos(s) != null) {
            conexionBasesDatos c = new conexionBasesDatos();
            String out = new Gson().toJson(cs.eliminarSucursal(c, s));
            return Response.status(Response.Status.OK).entity(out).build();

        } else {
            String out = "Error";
            return Response.status(Response.Status.OK).entity(out).build();

        }
    }

    public static Sucursal verificarDatos(Sucursal s) throws Exception {
        conexionBasesDatos c = new conexionBasesDatos();
        ComandosSucursales cs = new ComandosSucursales();
        if (cs.obtenerSucursal(c, s) != null) {
            Sucursal encontrada = cs.obtenerSucursal(c, s);
            return encontrada;
        } else {
            return null;
        }

    }
}

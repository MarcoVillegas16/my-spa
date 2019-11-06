/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.baseDatos.comandos;

import edu.softech.MySpa.baseDatos.conexionBasesDatos;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author marco
 */
public class comandosCliente {

    PreparedStatement ps;
    ResultSet rs;
    Statement stmt;
    conexionBasesDatos conn = new conexionBasesDatos();
    String query;

    public boolean validarCliente(Cliente c) throws SQLException, Exception {
        Usuario u = c.getUsuario();

        query = "SELECT * FROM v_buscarCliente WHERE nombreUsuario=?";
        boolean aprobado = true;
        conn.Conectar();
        ps = conn.getConexión().prepareStatement(query);
        ps.setString(1, u.getNombreUsuario());
        rs = ps.executeQuery();

        if (rs.next()) {
            aprobado = false;//Si hay otro cliente con el mismo correo
        }

        conn.Desconectar();

        return aprobado;
    }

    public Cliente registrarCliente(Cliente c, int opcion) throws SQLException, Exception {
        Usuario u = c.getUsuario();

        if (!validarCliente(c)) {

            return null;
        }
        query = "CALL insertarCliente (?,?,?,?,?,?,?,?,?,?,?,?,@uno,@dos,@tres)";

        conn.Conectar();
        ps = conn.getConexión().prepareStatement(query);

        //Datos personales
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getApellidoPaterno());
        ps.setString(3, c.getApellidoMaterno());
        ps.setString(4, c.getGenero());
        ps.setString(5, c.getDomicilio());
        ps.setString(6, c.getTelefono());
        ps.setString(7, c.getRfc());

        //Datos usuario
        ps.setString(8, u.getNombreUsuario());
        ps.setString(9, u.getContrasenia());
        ps.setString(10, u.getRol());

        //Datos cliente
        ps.setString(11, c.getNumeroUnico());
        ps.setString(12, c.getCorreo());

        rs = ps.executeQuery();

        c = buscarCliente(c, opcion);

        conn.Desconectar();

        return c;
    }

    public Cliente buscarCliente(Cliente c, int opcion) throws SQLException, Exception {

        Usuario u = c.getUsuario();
        query = "SELECT * FROM v_buscarCliente WHERE ";

        conn.Conectar();

        switch (opcion) {
            case 1:
                query += "nombreUsuario LIKE ?";
                ps = conn.getConexión().prepareStatement(query);
                ps.setString(1, u.getNombreUsuario());
                break;
            case 2:
                query += "numeroUnico=?";
                ps = conn.getConexión().prepareStatement(query);
                ps.setString(1, "" + c.getNumeroUnico());
                break;
            default:
                return null;
        }

        rs = ps.executeQuery();

        u = null;
        c = null;
        rs.next();

        u = new Usuario(rs.getInt("idUsuario"), rs.getString("nombreUsuario"),
                rs.getString("contrasenia"), rs.getString("rol"));

        c = new Cliente(rs.getInt("idCliente"), rs.getString("numeroUnico"),
                rs.getString("correo"), rs.getInt("estatus"), u, rs.getInt("idPersona"),
                rs.getString("nombre"), rs.getString("apellidoPaterno"),
                rs.getString("apellidoMaterno"), rs.getString("genero"),
                rs.getString("domicilio"), rs.getString("telefono"),
                rs.getString("rfc"));

        conn.Desconectar();

        return c;
    }

    public Queue buscarClientes() throws SQLException, Exception {
        Cliente c = null;
        Usuario u = null;
        Queue clientes = new LinkedList<Cliente>();
        query = "SELECT * FROM v_buscarCliente";

        conn.Conectar();

        ps = conn.getConexión().prepareStatement(query);

        rs = ps.executeQuery();

        u = null;
        c = null;

        while (rs.next()) {
            u = new Usuario(rs.getInt("idUsuario"), rs.getString("nombreUsuario"),
                    rs.getString("contrasenia"), rs.getString("rol"));
            c = new Cliente(rs.getInt("idCliente"), rs.getString("numeroUnico"),
                    rs.getString("correo"), rs.getInt("estatus"), u, rs.getInt("idPersona"),
                    rs.getString("nombre"), rs.getString("apellidoPaterno"),
                    rs.getString("apellidoMaterno"), rs.getString("genero"),
                    rs.getString("domicilio"), rs.getString("telefono"),
                    rs.getString("rfc"));
            clientes.add(c);

        }

        conn.Desconectar();

        return clientes;
    }

    public Cliente actualizarCliente(Cliente c, int opcion) throws SQLException, Exception {
        Usuario u = c.getUsuario();
        query = "call actualizarCliente( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                + "@uno,@dos,@tres)";

        conn.Conectar();

        ps = conn.getConexión().prepareStatement(query);

        ps.setString(1, c.getNombre());
        ps.setString(2, c.getApellidoPaterno());
        ps.setString(3, c.getApellidoMaterno());
        ps.setString(4, c.getGenero());
        ps.setString(5, c.getDomicilio());
        ps.setString(6, c.getTelefono());
        ps.setString(7, c.getRfc());
        ps.setString(8, u.getNombreUsuario());
        ps.setString(9, u.getContrasenia());
        ps.setString(10, c.getNumeroUnico());
        ps.setString(11, c.getCorreo());

        rs = ps.executeQuery();

        c = buscarCliente(c, opcion);

        return c;
    }

    public boolean borrarCliente(Cliente c) throws SQLException, Exception {
        boolean respuesta = false;

        query = "call eliminarCliente(?,@uno)";

        conn.Conectar();

        ps = conn.getConexión().prepareStatement(query);

        ps.setString(1, c.getNumeroUnico());

        rs = ps.executeQuery();

        respuesta = true;

        return respuesta;

    }

}

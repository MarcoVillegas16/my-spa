package edu.softech.MySpa.baseDatos.comandos;

import edu.softech.MySpa.modelo.Sucursal;
import java.sql.ResultSet;
import java.sql.Statement;
import edu.softech.MySpa.baseDatos.conexionBasesDatos;

public class ComandosSucursales {

    public static boolean ejecutarQuery(conexionBasesDatos c, String q) throws Exception {
        Statement stmt = null;
        c.Conectar();
        ResultSet rs = null;
        stmt = c.getConexión().createStatement();
        if (stmt.executeUpdate(q) != 0) {
            c.Desconectar();
            return true;
        } else {
            c.Desconectar();
            return true;
        }

    }

    public Sucursal insertarSucursal(conexionBasesDatos c, Sucursal s) throws Exception {
        Statement stmt = null;

        String query = "CALL insertarSucursal(" + "'" + s.getNombre() + "', '" + s.getDomicilio() + "', " + s.getLatitud() + ", " + s.getLongitud() + "," + s.getEstatus() + ")";
        c.Conectar();
        stmt = c.getConexión().createStatement();
        System.out.println(query);
        stmt.executeUpdate(query);
        c.Desconectar();
        return s;
    }

    public Sucursal actualizarSucursal(conexionBasesDatos c, Sucursal s) throws Exception {
        Statement stmt = null;

        String query = "CALL actualizarSucursal('" + s.getNombre() + "', '" + s.getDomicilio() + "', " + s.getLatitud() + ", " + s.getLongitud() + ", " + s.getIdSucursal() + ")";
        c.Conectar();
        stmt = c.getConexión().createStatement();
        System.out.println(query);
        stmt.executeUpdate(query);
        c.Desconectar();
        Sucursal su = obtenerSucursal(c, s);
        return su;
    }

    public Sucursal eliminarSucursal(conexionBasesDatos c, Sucursal s) throws Exception {
        Statement stmt = null;
        String query = "CALL eliminarSucursal(" + s.getIdSucursal() + ")";
        c.Conectar();
        stmt = c.getConexión().createStatement();
        System.out.println(query);
        stmt.executeUpdate(query);
        c.Desconectar();
        Sucursal su = obtenerSucursal(c, s);
        return su;
    }

    public static Sucursal[] obtenerLista(conexionBasesDatos c) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        Sucursal[] sucursales = null;

        int renglones = 0;
        int cont = 0;
        String query = "SELECT * FROM v_listarSucursales";
        c.Conectar();
        stmt = c.getConexión().createStatement();
        System.out.println(query);
        rs = stmt.executeQuery(query);
        if (rs.last()) {
            renglones = rs.getRow();
            sucursales = new Sucursal[renglones];
            rs.beforeFirst();
            while (rs.next()) {
                Sucursal p = new Sucursal();
                p.setIdSucursal(rs.getInt("idSucursal"));
                p.setNombre(rs.getString("nombre"));
                p.setDomicilio(rs.getString("domicilio"));
                p.setLatitud(rs.getDouble("latitud"));
                p.setLongitud(rs.getDouble("longitud"));
                p.setEstatus(rs.getInt("estatus"));
                sucursales[cont] = p;
                cont++;
            }
        }
        rs.close();
        stmt.close();
        c.Desconectar();
        return sucursales;
    }

    public Sucursal obtenerSucursal(conexionBasesDatos c, Sucursal s) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        Sucursal encontrado = null;

        String query = "SELECT * FROM v_listarSucursales WHERE idSucursal='" + s.getIdSucursal() + "'";
        c.Conectar();
        stmt = c.getConexión().createStatement();
        rs = stmt.executeQuery(query);
        if (rs.last()) {
            encontrado = new Sucursal();

            encontrado.setIdSucursal(rs.getInt("idSucursal"));
            encontrado.setNombre(rs.getString("nombre"));
            encontrado.setDomicilio(rs.getString("domicilio"));
            encontrado.setLatitud(rs.getDouble("latitud"));
            encontrado.setLongitud(rs.getDouble("longitud"));
            encontrado.setEstatus(rs.getInt("estatus"));

        }
        rs.close();
        stmt.close();
        c.Desconectar();
        return encontrado;
    }

}

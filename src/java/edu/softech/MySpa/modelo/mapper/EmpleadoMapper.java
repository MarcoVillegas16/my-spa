/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.modelo.mapper;

import edu.softech.MySpa.jdbc_template.core.interfaces.RowMapper;
import edu.softech.MySpa.modelo.Empleado;
import edu.softech.MySpa.modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Esau
 */
public class EmpleadoMapper implements RowMapper<Empleado>
{

    @Override
    public Empleado mapRow(ResultSet rs, int numRow) throws SQLException
    {
        /*
         * Empleado atributos
         */
        int idEmpleado = rs.getInt("idEmpleado");
        String numeroEmpleado = rs.getString("numeroEmpleado");
        String puesto = rs.getString("puesto");
        int estatus = rs.getInt("estatus");
        String foto = rs.getString("foto");
        String rutaFoto = rs.getString("rutaFoto");
        
        /*
         * Persona atributos
         */
        int idPersona = rs.getInt("idPersona");
        String nombre = rs.getString("nombre");
        String apellidoPaterno = rs.getString("apellidoPaterno");
        String apellidoMaterno = rs.getString("apellidoMaterno");
        String genero = rs.getString("genero");
        String domicilio = rs.getString("domicilio");
        String telefono = rs.getString("telefono");
        String rfc = rs.getString("rfc");
        
        /*
         * Usuario atributos
         */
        int idUsuario = rs.getInt("idUsuario");
        String nombreUsuario = rs.getString("nombreUsuario");
        String contrasenia = rs.getString("contrasenia");
        String rol = rs.getString("rol");
        
        Empleado empleado = new Empleado(
                idEmpleado, 
                numeroEmpleado, 
                puesto, 
                estatus, 
                foto, 
                rutaFoto, 
                new Usuario(idUsuario, nombreUsuario, contrasenia, rol), 
                idPersona, 
                nombre, 
                apellidoPaterno, 
                apellidoMaterno, 
                genero, 
                domicilio, 
                telefono, 
                rfc);

        // se recibe todos los datos del resultset
        return empleado;
    }
    
}

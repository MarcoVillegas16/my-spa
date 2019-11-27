/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.baseDatos.comandos;

import edu.softech.MySpa.baseDatos.conexionBasesDatos;
import edu.softech.MySpa.jdbc_template.core.implementation.JDBCTemplate;
import edu.softech.MySpa.jdbc_template.core.interfaces.CRUD;
import edu.softech.MySpa.jdbc_template.core.interfaces.RowMapper;
import edu.softech.MySpa.modelo.Empleado;
import edu.softech.MySpa.modelo.Usuario;
import edu.softech.MySpa.rowmapper.EmpleadoMapper;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Esau
 */
public class ComandosEmpleado implements CRUD<Empleado>
{

    private JDBCTemplate<Empleado> jdbcTemplate = new JDBCTemplate<>();
    private conexionBasesDatos con = new conexionBasesDatos();

    // metodo buscar por numeroUnicoEmpleado, nombreUsuario (String)
    public Empleado getByCredentials(Empleado empleado,
                                     int opcion)
            throws SQLException, Exception
    {
        con.Conectar();

        String sql = "SELECT * FROM vw_empleado where ";
        switch (opcion)
        {
            case 1:
                sql += "nombreUsuario=?";
                break;
            case 2:
                sql += "numeroEmpleado=?";
                break;
        }
        PreparedStatement stmt = con.getConexión().prepareStatement(sql);
        stmt.setString(1,
                opcion == 1 // operador ternario
                        ? empleado.getUsuario().getNombreUsuario() : empleado.getNumeroEmpleado());

        empleado = jdbcTemplate.executeQueryForObjcet(stmt, new EmpleadoMapper());

        return empleado;
    }

    @Override
    public Empleado getByID(long id)
            throws SQLException, Exception
    {
        con.Conectar();
        // alojará empleado encontrado
        Empleado empleado = null;

        String sql = "SELECT * FROM v_empleado where idEmpleado=?";
        PreparedStatement ps = con.getConexión().prepareStatement(sql);
        ps.setInt(1, (int) id);

        empleado = jdbcTemplate.executeQueryForObjcet(ps,
                // lamda
                (RowMapper) (ResultSet rs, int numRow) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });

        return empleado;
    }

    @Override
    public List<Empleado> read()
            throws SQLException, Exception
    {
        con.Conectar();
        String sql = "SELECT * FROM vw_empleado";
        PreparedStatement stmt = con.getConexión().prepareStatement(sql);

        List content = jdbcTemplate.executeQuery(stmt, new EmpleadoMapper());

        return content;
    }

    @Override
    public List<Empleado> read(String filter)
            throws SQLException, Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Empleado t)
            throws SQLException, Exception
    {
        con.Conectar();
        String sql = "{call crearEmpleado(?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?)}";
        CallableStatement stmt = con.getConexión().prepareCall(sql);

        asignarAStatementCrear(stmt, t);

        stmt.registerOutParameter(16, java.sql.Types.INTEGER);
        stmt.registerOutParameter(17, java.sql.Types.INTEGER);
        stmt.registerOutParameter(18, java.sql.Types.INTEGER);
        stmt.executeUpdate();

        //int datos;
        //int idPersona = stmt.getInt(16);
        //int idUsuario = stmt.getInt(17);
        //int idEmpleado = stmt.getInt(18);
    }

    @Override
    public void update(Empleado t)
            throws SQLException, Exception
    {
        con.Conectar();
        String sql = "{call actualizarEmpleado(?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?,?,?,"
                + "?,?,?)}";
        CallableStatement stmt = con.getConexión().prepareCall(sql);

        asignarAStatementActualizar(stmt, t);

        stmt.executeUpdate();
    }

    public void deleteUnique(String numeroEmpleado)
            throws Exception, SQLException
    {
        con.Conectar();
        String sql = "{call eliminarEmpleado(?)}";

        CallableStatement stmt = con.getConexión().prepareCall(sql);

        stmt.setString(1, numeroEmpleado);

        stmt.executeUpdate();
    }

    @Override
    public void delete(long id)
            throws SQLException, Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void asignarAStatementCrear(CallableStatement stmt,
                                        Empleado t)
            throws SQLException
    {
        //stmt.setInt(1, t.getIdPersona());
        stmt.setString(1, t.getNombre());
        stmt.setString(2, t.getApellidoPaterno());
        stmt.setString(3, t.getApellidoMaterno());
        stmt.setString(4, t.getGenero());
        stmt.setString(5, t.getDomicilio());
        stmt.setString(6, t.getTelefono());
        stmt.setString(7, t.getRfc());

        Usuario u = t.getUsuario();
        //stmt.setInt(9, u.getIdUsuario());
        stmt.setString(8, u.getNombreUsuario());
        stmt.setString(9, u.getContrasenia());
        stmt.setString(10, u.getRol());

        //stmt.setInt(13, t.getIdEmpleado());
        stmt.setString(11, t.getNumeroEmpleado());
        stmt.setString(12, t.getPuesto());
        stmt.setInt(13, t.getEstatus());
        stmt.setString(14, t.getFoto());
        stmt.setString(15, t.getRutaFOto());
    }

    private void asignarAStatementActualizar(CallableStatement stmt,
                                             Empleado t)
            throws SQLException
    {
        stmt.setInt(1, t.getIdPersona());
        stmt.setString(2, t.getNombre());
        stmt.setString(3, t.getApellidoPaterno());
        stmt.setString(4, t.getApellidoMaterno());
        stmt.setString(5, t.getGenero());
        stmt.setString(6, t.getDomicilio());
        stmt.setString(7, t.getTelefono());
        stmt.setString(8, t.getRfc());

        Usuario u = t.getUsuario();
        stmt.setInt(9, u.getIdUsuario());
        stmt.setString(10, u.getNombreUsuario());
        stmt.setString(11, u.getContrasenia());
        stmt.setString(12, u.getRol());

        stmt.setInt(13, t.getIdEmpleado());
        stmt.setString(14, t.getNumeroEmpleado());
        stmt.setString(15, t.getPuesto());
        stmt.setInt(16, t.getEstatus());
        stmt.setString(17, t.getFoto());
        stmt.setString(18, t.getRutaFOto());
    }
}

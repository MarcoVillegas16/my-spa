/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.jdbc_template.core.implementation;

import com.mysql.cj.jdbc.CallableStatement;
import edu.softech.MySpa.jdbc_template.core.interfaces.RowMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Esau
 */
public class JDBCTemplate<T>
{
    /*
     * Metodo NO funcional para DELETE, UPDATE
     */
    public void executeUpdate(PreparedStatement ps) throws SQLException
    {
        ps.execute();
        
        ps.close();
        //ps.getConnection().close();
    }
    
    /*
     * Metodo NO funcional para llamar procedimientos
     * revisión
     */
    public Stack updateCallable(CallableStatement cs) {
        return null;
    }

    /*
     * Metodo funcional para SELECT de varias tuplas
     */
    public List<T> executeQuery(PreparedStatement ps, RowMapper rm) throws SQLException
    {
        ResultSet rs = ps.executeQuery();

        List<T> list = new ArrayList<T>();
        while (rs.next())
        {
            list.add((T) rm.mapRow(rs, rs.getRow()));
        }
        
        ps.close();
        //ps.getConnection().close();

        return list;
    }
    
    /*
     * Metodo funcional para SELECT de una tupla
     */
    public T executeQueryForObjcet(PreparedStatement ps, RowMapper rm) throws SQLException
    {
        ResultSet rs = ps.executeQuery();
        T t = null;

        if (rs.first())
            t =  (T) rm.mapRow(rs, rs.getRow());
        
        ps.close();
        //ps.getConnection().close();
        
        return t;
    }
}

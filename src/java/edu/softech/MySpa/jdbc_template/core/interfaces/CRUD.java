/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.jdbc_template.core.interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Esau
 */
public interface CRUD<T>
{
    public T getByID(long id) throws SQLException, Exception;
    
    public List<T> read() throws SQLException, Exception;
    
    public List<T> read(String filter) throws SQLException, Exception;
    
    public void create(T t) throws SQLException, Exception;
    
    void update(T t) throws SQLException, Exception;
    
    void delete(long id) throws SQLException, Exception;
}

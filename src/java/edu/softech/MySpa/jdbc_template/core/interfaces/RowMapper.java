/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.jdbc_template.core.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Esau
 */
public interface RowMapper<T>
{
    public T mapRow(ResultSet rs, int numRow) throws SQLException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.baseDatos;

import java.sql.*;

public class conexionBasesDatos {

    private Connection conn;

    public conexionBasesDatos() {
        try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Conectar() throws Exception {
        String usuario = "root";
        String contrasenia = "root";
        String ubicacion = "jdbc:mysql://127.0.0.1:3306/myspa?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        // conn=DriverManager.getConnection(ubicacion, usuario, contrasenia);
        conn = DriverManager.getConnection(ubicacion, usuario, contrasenia);
    }

    public void Desconectar() throws Exception {
        conn.close();
    }

    public Connection getConexión() {
        return conn;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.controlador.validarDatos;

import edu.softech.MySpa.baseDatos.comandos.comandosCliente;
import edu.softech.MySpa.modelo.Cliente;
import edu.softech.MySpa.modelo.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author marco
 */
public class ControladorCliente {

    comandosCliente comC = new comandosCliente();

    Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    Matcher matcher;

    public Cliente crearCliente(ArrayList datos, int opcion) {
        Cliente c = null;
        int acum = 0;
        try {

            if (validarDatosObligatorio(datos)) {
                if (validarDatos(datos)) {
                    c = crearObjCliente(datos, opcion);

                    switch (opcion) {
                        case 1:
                            c = comC.registrarCliente(c, 1);
                            break;
                        case 2:
                            if (c.getNumeroUnico().length() == 15) {
                                c = comC.actualizarCliente(c, 2);
                            } else {
                                return null;
                            }
                            break;
                    }

                }
            }

        } catch (SQLException ex) {
            c = null;
            Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            c = null;
            Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;

    }

    public boolean validarDatosObligatorio(ArrayList datos) {

        if (datos.get(0) != "") {//Nombre
            if (((String) datos.get(0)).length() <= 64) {
                if (datos.get(1) != "") {//Apellido Paterno
                    if (((String) datos.get(1)).length() <= 64) {
                        if (datos.get(6) != "") {//Nombre de Usuario
                            if (((String) datos.get(6)).length() <= 48) {
                                if (datos.get(8) != "") {//Contraseña
                                    if (((String) datos.get(6)).length() <= 48) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
        return false;
    }

    public boolean validarDatos(ArrayList datos) {
        if (((String) datos.get(2)).length() <= 64) {//Apellido materno
            if (((String) datos.get(3)).length() <= 2) {//genero
                if (((String) datos.get(4)).length() <= 25) {//telefono
                    if (((String) datos.get(5)).length() == 13 || ((String) datos.get(5)).length() == 0) {//rfc
                        if (((String) datos.get(7)).length() <= 200) {//correo
                            matcher = pattern.matcher((String) datos.get(7));
                            if (matcher.find()) {
                                if (((String) datos.get(8)).length() <= 48) {//contrasenia
                                    if (((String) datos.get(9)).length() <= 200) {//Domicilio
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public Cliente crearObjCliente(ArrayList datos, int opcion) {

        Usuario u = new Usuario(0, (String) datos.get(6), (String) datos.get(8),
                "Cliente");
        
        //Sin numero unico
        Cliente c = new Cliente(0, (String) datos.get(7), 1, u, 0,
                (String) datos.get(0), (String) datos.get(1), (String) datos.get(2),
                (String) datos.get(3), (String) datos.get(9), (String) datos.get(4),
                (String) datos.get(5));
        switch (opcion) {
            case 1:
                break;
            case 2:
                c.setNumeroUnico((String) datos.get(10));
                break;
        }
        return c;
    }

    public boolean borrarCliente(ArrayList datos) throws Exception {

        boolean respuesta = false;
        Usuario u = new Usuario();
        u.setNombreUsuario((String) datos.get(1));

        Cliente c = new Cliente();
        c.setNumeroUnico((String) datos.get(0));

        Cliente c1 = comC.buscarCliente(c, 1);

        Cliente c2 = comC.buscarCliente(c, 2);

        if (c1.getIdCliente() == c2.getIdCliente()) {
            respuesta = comC.borrarCliente(c1);
        }

        return respuesta;
    }

    public Cliente buscarCliente(ArrayList datos, int opcion) throws Exception {

        Usuario u = new Usuario(0, (String) datos.get(0), (String) datos.get(1), "");

        Cliente c = new Cliente();
        c.setUsuario(u);

        c = comC.buscarCliente(c, opcion);

        u = c.getUsuario();

        if (u.getContrasenia().equals(datos.get(1)) && c.getEstatus() != 0 && u.getRol().equals("Cliente")) {
            return c;
        }

        return null;
    }

    public Queue buscarClientes() throws Exception {

        return comC.buscarClientes();
    }
}

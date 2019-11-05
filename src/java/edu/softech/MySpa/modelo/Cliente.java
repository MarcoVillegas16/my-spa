package edu.softech.MySpa.modelo;

import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marco
 */
public final class Cliente extends Persona {

    private int idCliente;
    private String numeroUnico;
    private String correo;
    private int estatus;

    //Ya tiene el numero unico
    public Cliente(int idCliente, String numeroUnico, String correo, int estatus, int idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, String genero, String domicilio, String telefono, String rfc, Usuario usuario) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc, usuario);
        this.idCliente = idCliente;
        this.numeroUnico = numeroUnico;
        this.correo = correo;
        this.estatus = estatus;

    }

    //Generar numero unico
    public Cliente(int idCliente, String correo, int estatus, int idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, String genero, String domicilio, String telefono, String rfc, Usuario usuario) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc, usuario);
        this.idCliente = idCliente;
        this.correo = correo;
        this.estatus = estatus;

        generarNumeroUnico();

    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    protected void generarNumeroUnico() {
        LocalDate date;

        String numeroUnico;
        String rfc = this.getRfc();

        if (!"".equals(rfc)) {

            numeroUnico = rfc.substring(0, 4);

        } else {

            numeroUnico = generarPrimerasLetrasRFC();

        }
        numeroUnico += "-";

        numeroUnico += date = LocalDate.now();

        this.numeroUnico = numeroUnico.toUpperCase();

    }

    protected String generarPrimerasLetrasRFC() {
        //1° Primera letra del apellido Paterno
        String respuesta = this.getApellidoPaterno().substring(0, 1);

        String apellidoPaterno = this.getApellidoPaterno();
        String apellidoMaterno = this.getApellidoMaterno();
        String nombre = this.getNombre();

        char vocales[] = apellidoPaterno.toCharArray();

        //2° Primera vocal del apellido paterno
        for (char letra : vocales) {
            if (letra == 'a' || letra == 'e'
                    || letra == 'i' || letra == 'o' || letra == 'u') {
                respuesta += letra;
                break;
            }
        }

        //Si no hay vocal se usa x
        if (respuesta.length() == 1) {
            respuesta += "x";
        }

        //3°Primera letra del apellido materno, si no existe se usa x
        if (!"".equals(apellidoMaterno)) {
            respuesta += apellidoMaterno.substring(0, 1);
        } else {
            respuesta += "x";
        }

        //4°Primera letra del primer nombre
        respuesta += nombre.substring(0, 1);

        return respuesta;

    }

}

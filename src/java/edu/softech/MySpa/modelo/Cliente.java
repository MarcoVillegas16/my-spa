package edu.softech.MySpa.modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marco
 */
public class Cliente extends Persona {

    private int idCliente;
    private String correoE;

    public Cliente(int idCliente, String correoE, int idPersona, String nombre, 
            String apellidoPaterno, String apellidoMaterno, String genero, 
            String telefonoCelular, String telefonoCasa, String rfc, 
            Usuario usuario, Domicilio domicilio) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, 
                telefonoCelular, telefonoCasa, rfc, usuario, domicilio);
        this.idCliente = idCliente;
        this.correoE = correoE;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCorreoE() {
        return correoE;
    }

    public void setCorreoE(String correoE) {
        this.correoE = correoE;
    }

}

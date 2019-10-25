/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.MySpa.modelo;

/**
 *
 * @author marco
 */
public class Domicilio {
    
    private int idDomicilio;
    private String calle;
    private String numeroCasa;
    private String colonia;

    public Domicilio(int idDomicilio, String calle, String numeroCasa, String colonia) {
        this.idDomicilio = idDomicilio;
        this.calle = calle;
        this.numeroCasa = numeroCasa;
        this.colonia = colonia;
    }

    public int getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(int idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }
    
    
    
}

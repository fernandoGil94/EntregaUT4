// Clase que permite relacionar una mascota con los piensos que le gustan
package com.fernando.Mapeado;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class Pienso {
    @BsonProperty
    private String nombre;
    @BsonProperty
    private String marca;
    @BsonProperty
    private List<String> componentes;

    public Pienso(String nombre, String marca, List<String> componentes) {
        this.nombre = nombre;
        this.marca = marca;
        this.componentes = componentes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public List<String> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = componentes;
    }

    @Override
    public String toString() {
        return "Pienso{" +
                "nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", componentes=" + componentes +
                '}';
    }
}

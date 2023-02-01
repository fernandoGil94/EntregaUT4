package com.fernando.Mapeado;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Gato {
    @BsonId
    private ObjectId id;
    @BsonProperty(value = "nombre")
    private String nombre;
    @BsonProperty(value = "raza")
    private String raza;
    @BsonProperty(value = "peso")
    private double peso;
    @BsonProperty(value = "piensos")
    private List<Pienso> piensos;

    public Gato(){
        this.piensos = new ArrayList<>();
    }
    public Gato(String nombre, String raza, double peso, List<Pienso> piensos){
        this.nombre = nombre;
        this.raza = raza;
        this.peso = peso;
        this.piensos = piensos;
    }

    public ObjectId get_Id() {
        return id;
    }

    public void set_Id(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public List<Pienso> getPiensos() {
        return piensos;
    }
    public void addPienso(Pienso pienso){
        this.piensos.add(pienso);
    }

    public void setPiensos(List<Pienso> piensos) {
        this.piensos = piensos;
    }

    @Override
    public String toString() {
        return "Gato{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", peso=" + peso +
                ", piensos=" + piensos +
                '}';
    }
}

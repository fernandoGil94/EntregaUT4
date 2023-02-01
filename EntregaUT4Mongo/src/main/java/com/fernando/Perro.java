package com.fernando;

public class Perro {
    private Long id;
    private String nombre;
    private String raza;
    private double peso;

    public Perro() {
    }

    public Perro(Long id, String nombre, String raza, double peso) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.peso = peso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "Perro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", peso=" + peso +
                '}';
    }
}

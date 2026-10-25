package models;

public class Vehiculo {
    int id;
    String marca;
    String modelo;
    String foto;
    int precio;

    public Vehiculo(int id, String marca, String modelo, String foto, int precio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.foto = foto;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getFoto() {
        return foto;
    }

    public int getPrecio() {
        return precio;
    }
}

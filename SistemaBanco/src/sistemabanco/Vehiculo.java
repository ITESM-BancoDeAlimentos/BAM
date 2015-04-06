/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabanco;

/**
 *
 * @author Enrique
 */
public class Vehiculo {
    private int IDVehiculo, Modelo, NoTarjeta, NoHolograma;
    private String Nombre, Marca, Descripcion, Clasificacion, Serie, Placas, Donante, Combustible;
    private boolean Funcionando;

    public Vehiculo(int IDVehiculo, int Modelo, int NoTarjeta, int NoHolograma, String Nombre, String Marca, String Descripcion, String Clasificacion, String Serie, String Placas, String Donante, String Combustible, boolean Funcionando) {
        this.IDVehiculo = IDVehiculo;
        this.Modelo = Modelo;
        this.NoTarjeta = NoTarjeta;
        this.NoHolograma = NoHolograma;
        this.Nombre = Nombre;
        this.Marca = Marca;
        this.Descripcion = Descripcion;
        this.Clasificacion = Clasificacion;
        this.Serie = Serie;
        this.Placas = Placas;
        this.Donante = Donante;
        this.Combustible = Combustible;
        this.Funcionando = Funcionando;
    }

    public int getIDVehiculo() {
        return IDVehiculo;
    }

    public void setIDVehiculo(int IDVehiculo) {
        this.IDVehiculo = IDVehiculo;
    }

    public int getModelo() {
        return Modelo;
    }

    public void setModelo(int Modelo) {
        this.Modelo = Modelo;
    }

    public int getNoTarjeta() {
        return NoTarjeta;
    }

    public void setNoTarjeta(int NoTarjeta) {
        this.NoTarjeta = NoTarjeta;
    }

    public int getNoHolograma() {
        return NoHolograma;
    }

    public void setNoHolograma(int NoHolograma) {
        this.NoHolograma = NoHolograma;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getClasificacion() {
        return Clasificacion;
    }

    public void setClasificacion(String Clasificacion) {
        this.Clasificacion = Clasificacion;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String Serie) {
        this.Serie = Serie;
    }

    public String getPlacas() {
        return Placas;
    }

    public void setPlacas(String Placas) {
        this.Placas = Placas;
    }

    public String getDonante() {
        return Donante;
    }

    public void setDonante(String Donante) {
        this.Donante = Donante;
    }

    public String getCombustible() {
        return Combustible;
    }

    public void setCombustible(String Combustible) {
        this.Combustible = Combustible;
    }

    public boolean isFuncionando() {
        return Funcionando;
    }

    public void setFuncionando(boolean Funcionando) {
        this.Funcionando = Funcionando;
    }
    
}

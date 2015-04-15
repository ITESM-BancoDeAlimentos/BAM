/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemabanco;

import java.util.Date;

/**
 *
 * @author Enrique
 */
public class Empleados {
    private int IDEmpleado;
    private double Salario;
    private String Nombre, Direccion, Telefono, Area, Puesto;
    private Date FechaNacimiento, FechaContratacion;

    public Empleados(int IDEmpleado, String Nombre, String Direccion, String Telefono, Date FechaNacimiento, double Salario, String Area, String Puesto, Date FechaContratacion) {
        this.IDEmpleado = IDEmpleado;
        this.Nombre = Nombre;
        this.Direccion = Direccion;
        this.Telefono = Telefono;
        this.FechaNacimiento = FechaNacimiento;
        this.Salario = Salario;
        this.Area = Area;
        this.Puesto = Puesto;
        this.FechaContratacion = FechaContratacion;
    }

    Empleados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIDEmpleado() {
        return IDEmpleado;
    }

    public void setIDEmpleado(int IDEmpleado) {
        this.IDEmpleado = IDEmpleado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public double getSalario() {
        return Salario;
    }

    public void setSalario(double Salario) {
        this.Salario = Salario;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public Date getFechaContratacion() {
        return FechaContratacion;
    }

    public void setFechaContratacion(Date FechaContratacion) {
        this.FechaContratacion = FechaContratacion;
    }
    
}

package com.empresa.app.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private int edad;
    private LocalDate fechaIngreso;
    private double salario;
    private boolean empleadoFijo;

    @ManyToOne
    private Departamento departamento;

    @ManyToOne
    private Puesto puesto;

    public Empleado() {}

    public Empleado(String nombre, int edad, LocalDate fechaIngreso, double salario, boolean empleadoFijo, Departamento departamento, Puesto puesto) {
        this.nombre = nombre;
        this.edad = edad;
        this.fechaIngreso = fechaIngreso;
        this.salario = salario;
        this.empleadoFijo = empleadoFijo;
        this.departamento = departamento;
        this.puesto = puesto;
    }

    public double calcularSalarioFinal() {
        return empleadoFijo ? salario * 1.08 : salario;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public boolean isEmpleadoFijo() { return empleadoFijo; }
    public void setEmpleadoFijo(boolean empleadoFijo) { this.empleadoFijo = empleadoFijo; }
    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }
    public Puesto getPuesto() { return puesto; }
    public void setPuesto(Puesto puesto) { this.puesto = puesto; }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s - %s - Salario final: %.2f", 
            nombre, edad, departamento.getNombre(), puesto.getNombre(), calcularSalarioFinal());
    }
}
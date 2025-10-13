package com.empresa.app.servicio;

import com.empresa.app.modelo.*;
import com.empresa.app.persistencia.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EmpleadoService {
    private EntityManager em = JpaUtil.getEntityManager();

    public void cargarDesdeCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            em.getTransaction().begin();

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty() || linea.startsWith("nombre")) continue;
                String[] campos = linea.split(",");

                String nombre = campos[0].trim();
                int edad = Integer.parseInt(campos[1].trim());
                LocalDate fecha = LocalDate.parse(campos[2].trim());
                double salario = Double.parseDouble(campos[3].trim());
                boolean fijo = Boolean.parseBoolean(campos[4].trim());
                String nombreDep = campos[5].trim();
                String nombrePuesto = campos[6].trim();

                Departamento dep = obtenerOCrearDepartamento(nombreDep);
                Puesto puesto = obtenerOCrearPuesto(nombrePuesto);

                Empleado emp = new Empleado(nombre, edad, fecha, salario, fijo, dep, puesto);
                em.persist(emp);
            }

            em.getTransaction().commit();
            System.out.println("Empleados cargados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }

    private Departamento obtenerOCrearDepartamento(String nombre) {
        Query q = em.createQuery("SELECT d FROM Departamento d WHERE d.nombre = :n");
        q.setParameter("n", nombre);
        List<Departamento> res = q.getResultList();
        if (res.isEmpty()) {
            Departamento d = new Departamento(nombre);
            em.persist(d);
            return d;
        }
        return res.get(0);
    }

    private Puesto obtenerOCrearPuesto(String nombre) {
        Query q = em.createQuery("SELECT p FROM Puesto p WHERE p.nombre = :n");
        q.setParameter("n", nombre);
        List<Puesto> res = q.getResultList();
        if (res.isEmpty()) {
            Puesto p = new Puesto(nombre);
            em.persist(p);
            return p;
        }
        return res.get(0);
    }

    public void listarEmpleados() {
        List<Empleado> empleados = em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
        empleados.forEach(System.out::println);
    }

    public void promedioPorDepartamento() {
        List<Object[]> resultados = em.createQuery(
                "SELECT e.departamento.nombre, AVG(e.salario) FROM Empleado e GROUP BY e.departamento.nombre", Object[].class)
                .getResultList();

        System.out.println("\n--- Promedio de salario por departamento ---");
        resultados.forEach(r -> System.out.printf("%s: %.2f%n", r[0], (Double) r[1]));
    }

    public void empleadoMasAntiguo() {
        Empleado e = em.createQuery(
                "SELECT e FROM Empleado e ORDER BY e.fechaIngreso ASC", Empleado.class)
                .setMaxResults(1)
                .getSingleResult();
        System.out.println("\nEmpleado con mayor antigüedad: " + e.getNombre() + " (" + e.getFechaIngreso() + ")");
    }

    public void resumenTipoEmpleado() {
        Long fijos = em.createQuery("SELECT COUNT(e) FROM Empleado e WHERE e.empleadoFijo = true", Long.class).getSingleResult();
        Long temporales = em.createQuery("SELECT COUNT(e) FROM Empleado e WHERE e.empleadoFijo = false", Long.class).getSingleResult();

        System.out.println("\nEmpleados fijos: " + fijos);
        System.out.println("Empleados temporales: " + temporales);
    }

    public void exportarResumenCSV() {
        List<Object[]> resultados = em.createQuery(
                "SELECT e.puesto.nombre, COUNT(e) FROM Empleado e GROUP BY e.puesto.nombre", Object[].class)
                .getResultList();

        try (PrintWriter pw = new PrintWriter(new FileWriter("resumen_puestos.csv"))) {
            pw.println("Puesto,Cantidad");
            for (Object[] r : resultados) {
                pw.printf("%s,%d%n", r[0], ((Long) r[1]));
            }
            System.out.println("\nArchivo resumen_puestos.csv generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrar() {
        if (em.isOpen()) em.close();
        JpaUtil.close();
    }
}

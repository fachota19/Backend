package com.empresa.app.app;

import com.empresa.app.servicio.EmpleadoService;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmpleadoService service = new EmpleadoService();

        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE EMPLEADOS ===");
            System.out.println("1. Cargar empleados desde CSV");
            System.out.println("2. Listar empleados");
            System.out.println("3. Promedio de salario por departamento");
            System.out.println("4. Empleado con mayor antigüedad");
            System.out.println("5. Cantidad de empleados fijos vs temporales");
            System.out.println("6. Exportar resumen CSV (por puesto)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> service.cargarDesdeCSV("empleados.csv");
                case 2 -> service.listarEmpleados();
                case 3 -> service.promedioPorDepartamento();
                case 4 -> service.empleadoMasAntiguo();
                case 5 -> service.resumenTipoEmpleado();
                case 6 -> service.exportarResumenCSV();
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 7);

        sc.close();
        service.cerrar();
    }
}
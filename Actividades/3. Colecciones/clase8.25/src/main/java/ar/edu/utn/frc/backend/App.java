package ar.edu.utn.frc.backend;

import java.time.LocalDate;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        // Crear un porcino
        Animal chancho = new Porcino(
                285.5f,                         // peso en kg
                LocalDate.of(2024, 5, 10),      // fecha de nacimiento
                Sexo.MACHO,
                RazaPorcino.KUNEKUNE
        );

        // Crear un bovino
        Animal vaca = new Bovino(
                450f,
                LocalDate.of(2023, 11, 3),
                Sexo.HEMBRA,
                RazaBovino.ANGUS
        );

        // Crear dietas con Map.of()
        ComposicionDieta dietaPorcino = new ComposicionDieta(
                "Dieta Porcino Base",
                "50% maíz, 30% soja, 20% trigo",
                Map.of(
                        Alimento.MAIZ, 50,
                        Alimento.SOJA, 30,
                        Alimento.TRIGO, 20
                )
        );

        ComposicionDieta dietaBovino = new ComposicionDieta(
                "Dieta Bovino Base",
                "40% maíz, 30% soja, 30% forraje",
                Map.of(
                        Alimento.MAIZ, 40,
                        Alimento.SOJA, 30,
                        Alimento.FORRAJE, 30
                )
        );

        // Crear el servicio de dietas
        ServicioDieta servicio = new ServicioDieta();

        // Asignar dietas a los animales
        servicio.asignar(chancho, dietaPorcino);
        servicio.asignar(vaca, dietaBovino);

        // Mostrar resultados
        System.out.println("\n=== PORCINO ===");
        System.out.println(chancho);
        System.out.println("Dieta asignada: " + servicio.obtener(chancho).getNombre());
        System.out.println("Composición: " + servicio.obtener(chancho).getComposicion());

        System.out.println("\n=== BOVINO ===");
        System.out.println(vaca);
        System.out.println("Dieta asignada: " + servicio.obtener(vaca).getNombre());
        System.out.println("Composición: " + servicio.obtener(vaca).getComposicion());
    }
}
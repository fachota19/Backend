package com.frc.backend;

import com.frc.backend.infra.DbInitializer;
import com.frc.backend.infra.LocalEntityManagerProvider;
import com.frc.backend.services.ImportService;
import com.frc.backend.services.ReportService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Aplicación principal - BoardGames App (Turno 2)
 * 
 * Inicializa la base de datos, importa los datos desde CSV
 * y ejecuta los reportes solicitados.
 */
public class App {

    public static void main(String[] args) {

        // ============================================================
        // 🔇 Desactivar logs de Hibernate y JBoss
        // ============================================================
        LogManager.getLogManager().reset();
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Logger.getLogger("org.jboss").setLevel(Level.OFF);
        Logger.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
        Logger.getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.OFF);

        System.out.println("=== 🧩 BOARD GAMES - TURNO 2 ===\n");

        // ============================================================
        // 1️⃣ Inicializar BD (ejecuta el script DDL)
        // ============================================================
        try {
            DbInitializer.init();
            System.out.println("[OK] Base de datos inicializada correctamente.");
        } catch (Exception e) {
            System.err.println("[FAIL] Error al inicializar la base de datos: " + e.getMessage());
            return;
        }

        // ============================================================
        // 2️⃣ Crear EntityManager y configurar servicios
        // ============================================================
        EntityManagerFactory emf = LocalEntityManagerProvider.getFactory();
        EntityManager em = emf.createEntityManager();

        try {
            // ============================================================
            // 3️⃣ Importar datos desde el archivo CSV
            // ============================================================
            ImportService.importarDatos("sql/board_games_data.csv");
            System.out.println("[OK] Importación completada correctamente.");

            // ============================================================
            // 4️⃣ Ejecutar reportes solicitados
            // ============================================================
            ReportService.mostrarPeoresCategorias(em);
            ReportService.mostrarAptosParaCuatro(em);

        } catch (Exception e) {
            System.err.println("\n[FAIL] Error durante la ejecución: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // ============================================================
            // 5️⃣ Cerrar conexiones
            // ============================================================
            if (em.isOpen()) em.close();
            emf.close();
        }

        System.out.println("\n=== ✅ FIN DE PROCESO ===");
    }
}
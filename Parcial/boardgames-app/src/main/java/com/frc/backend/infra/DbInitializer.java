package com.frc.backend.infra;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Ejecuta el script DDL (ddl_board_games.sql) desde resources/sql/
 * para inicializar la base de datos H2 en memoria.
 */
public class DbInitializer {

    public static void init() {
        DataSource ds = DataSourceProvider.getDataSource();

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            String ddl = new BufferedReader(
                    new InputStreamReader(
                            DbInitializer.class.getClassLoader()
                                    .getResourceAsStream("sql/ddl_board_games.sql")
                    )
            ).lines().collect(Collectors.joining("\n"));

            stmt.execute(ddl);
            System.out.println("[OK] Base de datos inicializada correctamente (script ejecutado).");

        } catch (Exception e) {
            System.err.println("[FAIL] Error al ejecutar DDL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

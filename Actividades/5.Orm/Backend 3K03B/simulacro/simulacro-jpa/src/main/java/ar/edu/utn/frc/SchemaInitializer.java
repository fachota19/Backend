package ar.edu.utn.frc;

import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class SchemaInitializer {

    // Crea un DataSource para conectarse a la BD en memoria
    static DataSource h2DataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:gamesdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_UPPER=false");
        ds.setUser("sa");
        ds.setPassword("");
        return ds;
    }

    // Lee y ejecuta ddl.sql
    public static void runDdl() {
        try (Connection con = h2DataSource().getConnection();
             Statement st = con.createStatement()) {

            String ddl = new BufferedReader(
                    new InputStreamReader(
                            SchemaInitializer.class.getClassLoader().getResourceAsStream("ddl.sql")))
                    .lines().collect(Collectors.joining("\n"));

            // Divide por ';' si hay varias sentencias en el ddl.sql
            for (String sql : ddl.split(";")) {
                String s = sql.trim();
                if (!s.isEmpty()) {
                    st.execute(s);
                }
            }
            System.out.println("[DDL] Ejecutado OK.");
        } catch (Exception e) {
            throw new RuntimeException("Error ejecutando ddl.sql", e);
        }
    }
}
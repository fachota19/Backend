package com.frc.backend.infra;

import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;

/**
 * Proveedor único de DataSource H2 en memoria.
 * Se usa tanto para JDBC como para JPA.
 */
public class DataSourceProvider {

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            JdbcDataSource ds = new JdbcDataSource();
            // Base en memoria con persistencia hasta que termine el proceso
            ds.setURL("jdbc:h2:mem:boardgames;DB_CLOSE_DELAY=-1");
            ds.setUser("sa");
            ds.setPassword("");
            dataSource = ds;
            System.out.println("[OK] DataSource H2 configurado en memoria.");
        }
        return dataSource;
    }
}

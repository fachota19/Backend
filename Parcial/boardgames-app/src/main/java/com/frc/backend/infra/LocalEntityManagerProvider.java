package com.frc.backend.infra;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Configura y expone un EntityManagerFactory local
 * usando el mismo DataSource H2 en memoria.
 */
public class LocalEntityManagerProvider {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getFactory() {
        if (emf == null) {
            Map<String, Object> props = new HashMap<>();
            props.put("javax.persistence.jdbc.driver", "org.h2.Driver");
            props.put("javax.persistence.jdbc.url", "jdbc:h2:mem:boardgames;DB_CLOSE_DELAY=-1");
            props.put("javax.persistence.jdbc.user", "sa");
            props.put("javax.persistence.jdbc.password", "");
            props.put("hibernate.hbm2ddl.auto", "none"); // el DDL lo ejecuta DbInitializer
            props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            props.put("hibernate.show_sql", "true");
            props.put("hibernate.format_sql", "true");

            emf = Persistence.createEntityManagerFactory("boardgamesPU", props);
            System.out.println("[OK] EntityManagerFactory configurado (JPA + H2 en memoria).");
        }
        return emf;
    }
}

package ar.edu.utn.frc.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {
    private static final EntityManagerFactory EMF = build();

    private static EntityManagerFactory build() {
        // El nombre "gamesPU" debe coincidir con persistence.xml
        return Persistence.createEntityManagerFactory("gamesPU");
    }

    public static EntityManager em() {
        return EMF.createEntityManager();
    }

    public static void close() {
        EMF.close();
    }

    private JpaUtil() {}
}
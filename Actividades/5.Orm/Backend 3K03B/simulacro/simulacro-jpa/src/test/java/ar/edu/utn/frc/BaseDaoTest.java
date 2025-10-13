package ar.edu.utn.frc;

import ar.edu.utn.frc.infrastructure.JpaUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseDaoTest {

    @BeforeAll
    static void beforeAll() {
        // Ejecuta DDL antes de cualquier test
        SchemaInitializer.runDdl();
    }

    @AfterAll
    static void afterAll() {
        // Cierra EMF una sola vez
        JpaUtil.close();
    }
}
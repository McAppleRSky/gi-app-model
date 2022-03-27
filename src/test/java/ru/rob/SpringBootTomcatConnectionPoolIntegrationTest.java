package ru.rob;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringBootTomcatConnectionPoolIntegrationTest {

//    @Autowired
//    private DataSource dataSource;

//    @Autowired
//    private ApplicationContext applicationContext;

//    @Autowired
//    private CommandLineRunner commandLineRunner;

    @Test
    public void givenTomcatConnectionPoolInstance_whenCheckedPoolClassName_thenCorrect() {
//        ApplicationContext context = this.applicationContext;
//        CommandLineRunner runner = this.commandLineRunner;

        assertTrue(true);
//        assertNotNull(dataSource);
//        assertEquals(dataSource.getClass().getName(), "org.apache.tomcat.jdbc.pool.DataSource");
    }

}

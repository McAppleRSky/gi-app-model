package ru.rob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// https://www.baeldung.com/spring-boot-tomcat-connection-pool
// https://habr.com/ru/post/572828/

@SpringBootApplication
/*@ComponentScan(basePackages = {
        "ru.rob.component",
        "ru.rob.service",
        "ru.rob.repository",
})*/
public class SpringBootConsoleApplication {

    private static Logger logger = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        logger.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class);
        logger.info("APPLICATION FINISHED");
    }
}

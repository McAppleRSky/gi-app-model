package ru.rob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// https://www.baeldung.com/spring-boot-tomcat-connection-pool
// https://habr.com/ru/post/572828/
// https://www.baeldung.com/spring-boot-failed-to-configure-data-source

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
public class SpringBootConsoleApplication {

    private static Logger logger = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        logger.info(" -> STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class);
        logger.info(" -> APPLICATION FINISHED");
    }

}

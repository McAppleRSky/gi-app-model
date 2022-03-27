package ru.rob.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.rob.service.ModelService;

@Component
public class CommandLineQueryRunner implements CommandLineRunner {

    private final Logger LOGGER = LoggerFactory.getLogger(CommandLineQueryRunner.class);

    @Autowired
    private ModelService modelService;

//    @Autowired private ApplicationContext applicationContext;

//    @Autowired private DataSource dataSource;

    public void run(String... args) throws Exception {
//        ApplicationContext ctx = this.applicationContext;
//        DataSource ds = this.dataSource;
        LOGGER.info("EXECUTING : command line runner");
//        sleep(1000);
        modelService.tryRequest();
    }

}

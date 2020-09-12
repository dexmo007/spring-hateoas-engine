package com.dexmohq.hateoas.state.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

//    private static final Log TEST_LOG = LogFactory.getLog("TEST_LOG");
//
//    private static void logStateEngine(String name, StateEngine<?, ?> stateEngine) throws Exception {
//        final Field definition = stateEngine.getClass().getDeclaredField("definition");
//        definition.setAccessible(true);
//        TEST_LOG.info(name + ": " + stateEngine.toString() + " with definition " + definition.get(stateEngine));
//    }
//
//    @Bean
//    CommandLineRunner runner(@Qualifier("taskStateEngine") StateEngine<Task, Task.State> taskStateEngine, @Qualifier("fooStateEngine") StateEngine<Foo, String> fooStringStateEngine) {
//        return args -> {
//            logStateEngine("taskStateEngine", taskStateEngine);
//            logStateEngine("fooStringStateEngine", fooStringStateEngine);
//        };
//    }

}

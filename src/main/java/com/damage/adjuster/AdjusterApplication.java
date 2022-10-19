package com.damage.adjuster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class AdjusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdjusterApplication.class, args);
    }

}

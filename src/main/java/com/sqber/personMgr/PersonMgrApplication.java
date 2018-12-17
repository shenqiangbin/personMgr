package com.sqber.personMgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@ComponentScan
public class PersonMgrApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonMgrApplication.class, args);
    }
}

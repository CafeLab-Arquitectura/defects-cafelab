package com.acme.defectscafelab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DefectsCafeLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(DefectsCafeLabApplication.class, args);
    }

}

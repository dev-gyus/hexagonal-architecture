package com.example.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@EnableMongoAuditing
@SpringBootApplication
public class HexagonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);
    }

}

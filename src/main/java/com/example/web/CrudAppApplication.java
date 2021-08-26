package com.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * The CrudAppApplication program implements an CRUD application that
 * provide REST controller with Basic authorization
 *
 * @author  Krzysztof Kubiś
 * @version 1.0
 * @since   JDK 11
 */
@EnableJpaRepositories("com.example.web.jpa.repositories")
@EntityScan("com.example.web.domain.models")
@SpringBootApplication
public class CrudAppApplication {

    /**
     * Main function that initialize whole program as SpringApplication
     * @param args The Array of arguments provided to program
     */
    public static void main(String[] args) {
        SpringApplication.run(CrudAppApplication.class, args);
    }

}

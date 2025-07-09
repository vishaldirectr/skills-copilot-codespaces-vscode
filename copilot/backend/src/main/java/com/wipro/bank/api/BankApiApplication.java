package com.wipro.bank.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot REST API for Digital Bank Application
 * Supports Web, iOS, and Android platforms
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wipro.bank"})
public class BankApiApplication {
    
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("    DIGITAL BANK REST API STARTING...         ");
        System.out.println("==============================================");
        
        SpringApplication.run(BankApiApplication.class, args);
        
        System.out.println("==============================================");
        System.out.println("    BANK API READY FOR CONNECTIONS           ");
        System.out.println("    Web: http://localhost:8080                ");
        System.out.println("    API Docs: http://localhost:8080/swagger-ui");
        System.out.println("==============================================");
    }
}

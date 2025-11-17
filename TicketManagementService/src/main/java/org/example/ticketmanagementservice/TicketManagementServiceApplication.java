package org.example.ticketmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TicketManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketManagementServiceApplication.class, args);
    }

}

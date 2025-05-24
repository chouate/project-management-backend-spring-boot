package com.hps.clientservice;

import com.hps.clientservice.entities.Client;
import com.hps.clientservice.services.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(ClientService clientService){
        return args -> {
//            Client client = new Client();
//            client.setName("BMCE");
//            client.setActivityDomain("Banque");
//            client.setContactName("mehdi echeouati");
//            client.setCode("code1");
//            client.setContactPhoneNumber("0378348498");
//            client.setContactEmail("mehdi@gmail.com");
//            client.setDirectorId(1L);
//            client.setProjectManagerId(2L);
//            clientService.createNewClient(client);
//
//            System.out.println("le client 1 enregisté: "+client);

//            Client clientIncomplete = new Client();
//            clientIncomplete.setContactName("Mehdi OSF");
            //clientService.createNewClient(clientIncomplete);
//            System.out.println("le client incomplete enregisté: "+clientIncomplete);
        };
    }

}

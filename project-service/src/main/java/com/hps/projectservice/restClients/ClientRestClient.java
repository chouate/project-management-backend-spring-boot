package com.hps.projectservice.restClients;


import com.hps.projectservice.models.Client;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-service")
public interface ClientRestClient {
    @CircuitBreaker(name = "clientService", fallbackMethod = "getDefaultClientById")
    @GetMapping("/api/clients/{id}")
    public Client getClientById(@PathVariable Long id);

    default Client getDefaultClientById(Long id, Exception exception){
        Client client = new Client();

        client.setId(id);
        client.setName("Name Not Available");
        client.setCode("Code Not Available");
        client.setActivityDomain("Activity Domain Not Available");
        return client;
    }


}

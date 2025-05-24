package com.hps.clientservice.controllers;

import com.hps.clientservice.dtos.ClientDTO;
import com.hps.clientservice.entities.Client;
import com.hps.clientservice.models.User;
import com.hps.clientservice.patchers.ClientPatcher;
import com.hps.clientservice.restClients.UserRestClient;
import com.hps.clientservice.services.ClientService;
import io.micrometer.core.ipc.http.HttpSender;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ClientController {
    private final ClientService clientService;
    private final UserRestClient userRestClient;

    private final ClientPatcher clientPatcher;

    public ClientController(ClientService clientService, UserRestClient userRestClient, ClientPatcher clientPatcher) {
        this.clientService = clientService;
        this.userRestClient = userRestClient;
        this.clientPatcher = clientPatcher;
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id){
        Client client = clientService.getClientById(id);
        if(client == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Client with Id: "+id+" Not found, try with other Id.");
        }
        User projectManager = userRestClient.getUserById(client.getProjectManagerId());
        User director = userRestClient.getUserById(client.getDirectorId());
        client.setDirector(director);
        client.setProjectManager(projectManager);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/clients/getAll")
    public List<Client> getAllClient(){
        List<Client> listClients = this.clientService.getAllClients();
        listClients.forEach(client -> {
            User director = this.userRestClient.getDirectorById(client.getDirectorId());
            User projectManager = this.userRestClient.getPMById(client.getProjectManagerId());
            client.setDirector(director);
            client.setProjectManager(projectManager);
        });
        return listClients;
    }

    @GetMapping("/directors/{directorId}/clients")
    public List<Client> getClientsByDirectorId(@PathVariable Long directorId){
        List<Client> listClients = this.clientService.getClientsByDirectorId( directorId);
        listClients.forEach(client -> {
            User director = this.userRestClient.getDirectorById(client.getDirectorId());
            User projectManager = this.userRestClient.getPMById(client.getProjectManagerId());
            client.setDirector(director);
            client.setProjectManager(projectManager);
        });
        return listClients;
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createNewClient(@Valid @RequestBody Client client){
        Client saved =this.clientService.createNewClient(client);

        User director = this.userRestClient.getDirectorById(saved.getDirectorId());
        User projectManager = this.userRestClient.getPMById(saved.getProjectManagerId());
        saved.setDirector(director);
        saved.setProjectManager(projectManager);

        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    @PatchMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient2(@PathVariable Long id, @RequestBody ClientDTO clientRequestBody){
        Client updatedClient = this.clientService.updateClient(id, clientRequestBody);

        User director = this.userRestClient.getDirectorById(updatedClient.getDirectorId());
        User projectManager = this.userRestClient.getPMById(updatedClient.getProjectManagerId());
        updatedClient.setDirector(director);
        updatedClient.setProjectManager(projectManager);

        return ResponseEntity.ok(updatedClient);

    }

    @DeleteMapping("/clients/{id}")
    public void deleteClientById(@PathVariable Long id){
        this.clientService.deleteClientById(id);
    }

}

package com.hps.clientservice.services;

import com.hps.clientservice.dtos.ClientDTO;
import com.hps.clientservice.entities.Client;
import com.hps.clientservice.exceptions.ResourceAlreadyExistException;
import com.hps.clientservice.exceptions.ResourceNotFoundException;
import com.hps.clientservice.models.User;
import com.hps.clientservice.patchers.ClientPatcher;
import com.hps.clientservice.repositories.ClientRepository;
import com.hps.clientservice.restClients.UserRestClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientPatcher clientPatcher;
    private final UserRestClient userRestClient;


    public ClientService(ClientRepository clientRepository, ClientPatcher clientPatcher, UserRestClient userRestClient) {
        this.clientRepository = clientRepository;
        this.clientPatcher = clientPatcher;
        this.userRestClient = userRestClient;
    }


    public List<Client> getAllClients(){
        return this.clientRepository.findAll();
    }

    public List<Client> getClientsByDirectorId(Long directorId){
        return this.clientRepository.findClientByDirectorId(directorId);
    }

    public List<Client> getClientsByPM(Long id){
        User pm = this.userRestClient.getPMById(id);
        System.out.println("pm = "+pm);
        if(pm.getId() == null){
            throw new ResourceNotFoundException("The project manager with id '"+id+"' Not found.");
        }
        return this.clientRepository.findClientByProjectManagerId(id);
    }


    public Client getClientById(Long id){
        return clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The client with id '"+id+"' Not found."));
    }

    public Client createNewClient(Client client){
        // Avant de rajouter un client on doit vérifier si le code n'existe pas
        // Before adding a new client we must verify if the code already exists or not.
        Optional<Client> optionalCustomer = this.clientRepository.findByCode(client.getCode());

        // Si le numéro on lève une exception personnalisée ResourceAlreadyExistException
        // if the code is found we Thrown a personalised exception ResourceAlreadyExistException
        if (optionalCustomer.isPresent())
            throw new ResourceAlreadyExistException("The code '" +client.getCode()+ "' already exists ");

        // Sinon on insère le client et on retourne ses information
        //else we add the client, and we return there information

        return this.clientRepository.save(client);
    }
    public Client updateClient(Long id, ClientDTO clientNewData){

        //throw ResourceNotFoundException personalized exception if the client to update not found in dataBase
        Client clientExisting = this.clientRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("The client with id '"+id+"', you are trying to update, does not exist."));

        //check if the code already exist in author client
        if (clientNewData.getCode()!=null){
            Client optionalClient = this.clientRepository.findByCode(clientNewData.getCode()).orElse(null);
            if(optionalClient != null ){
                if(optionalClient.getId() != clientExisting.getId()){
                    System.out.println("optionalClient.getId() = "+optionalClient.getId());
                    System.out.println("clientExisting.getId() = "+clientExisting.getId());
                    throw new ResourceAlreadyExistException("The code '"+clientNewData.getCode()+"' already exists.");
                }
            }
        }

        // Attempt to patch the existing client entity with new data using the custom patching logic.
        // The patching logic applies only the non-null fields from the DTO to the existing entity,
        // allowing partial updates while preserving unchanged values.
        // If successful, the patched client is saved to the repository.
        // If an IllegalAccessException occurs during the patching process (e.g., due to reflection access issues),
        // it is wrapped and rethrown as a RuntimeException to indicate a critical failure during update.

        // Tente de mettre à jour l'entité client existante avec les nouvelles données en utilisant une logique de patch personnalisée.
        // La logique de patch applique uniquement les champs non nuls du DTO à l'entité existante,
        // ce qui permet de faire des mises à jour partielles tout en conservant les valeurs inchangées.
        // Si l'opération réussit, le client mis à jour est enregistré dans le dépôt (repository).
        // Si une IllegalAccessException se produit pendant le processus de mise à jour (par exemple, en raison de problèmes d'accès via la réflexion),
        // elle est encapsulée et relancée en tant que RuntimeException pour indiquer une erreur critique lors de la mise à jour.

        try {
            Client patchedClient = this.clientPatcher.clientPatcher(clientExisting, clientNewData);
            return this.clientRepository.save(patchedClient);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error while patching client data", e);
        }

    }

    public void deleteClientById(Long id){
        Client client = this.clientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The client with id '"+id+"', you are trying to delete, does not exist."));
        this.clientRepository.deleteById(id);
    }

}

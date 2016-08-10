/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.client;

import be.nille.auth.server.model.InvalidCredentialsException;
import be.nille.auth.server.cryptography.StringGenerator;
import be.nille.auth.server.cryptography.UnsecureStringGenerator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nholvoet
 */
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    public Client getClient(final String clientId){
        return clientRepository.findByCredentialsClientId(clientId);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client register(final RegisterClientCommand command) {
        StringGenerator generator = new UnsecureStringGenerator(20);
        String clientId;
        Client retrievedClient;
        do {
            clientId = generator.generate();
            retrievedClient = clientRepository.findByCredentialsClientId(clientId);
        } while (retrievedClient != null);

        Client client = command.toClient(clientId);
        clientRepository.saveAndFlush(client);
        return client;

    }
    
    public Client authenticate(final Credentials credentials){
        Client client = clientRepository.findByCredentials(credentials);
        if(client == null){
            throw new InvalidCredentialsException("Bad credentials");
        }
        return client;
    }

}

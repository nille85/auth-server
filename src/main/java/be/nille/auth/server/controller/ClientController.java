/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.controller;

import be.nille.auth.server.model.RegisterClientCommand;
import be.nille.auth.server.model.Client;
import be.nille.auth.server.model.ClientService;
import be.nille.jwt.aspect.annotation.Authorize;
import be.nille.jwt.aspect.annotation.ClaimValue;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author nholvoet
 */
@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Client> getClients() {
        return clientService.getClients();
    }

    @Authorize("hasClaim('aud','#clientid')")
    @RequestMapping(value = "/{clientid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Client getClient(@PathVariable(value = "clientid") @ClaimValue(value = "clientid") final String clientId) {
        return clientService.getClient(clientId);
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Client registerClient(@RequestBody RegisterClientCommand command) {
        return clientService.register(command);
        

    }

}

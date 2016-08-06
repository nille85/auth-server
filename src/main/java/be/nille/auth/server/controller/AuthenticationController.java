/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.controller;

import be.nille.auth.server.model.Credentials;
import be.nille.auth.server.model.InvalidCredentialsException;
import be.nille.auth.server.model.Client;
import be.nille.auth.server.model.ClientRepository;
import be.nille.auth.server.model.ClientService;
import be.nille.jwt.components.model.JWT;
import be.nille.jwt.components.signer.JWTSigner;
import be.nille.jwt.components.model.Payload;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nholvoet
 */
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {
    
    private final ClientService clientService;
    private final JWTSigner jwtSigner;
    
    @Autowired
    public AuthenticationController(final ClientService clientService, final JWTSigner jwtSigner){
        this.clientService = clientService;
        this.jwtSigner = jwtSigner;
    }
    
    @RequestMapping(path = "/token", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody void authenticate(HttpServletResponse response, @RequestBody Credentials credentials) {
            
        Client client = clientService.authenticate(credentials);
        
        Payload payload =Payload.builder()
                .withClaim("iss", "NILLE")
                .withClaim("aud", client.getCredentials().getClientId())
                .build();
        JWT jwt = jwtSigner.sign(payload);
        response.addHeader("X-AUTH", jwt.getBase64EncodedValue());
    }
    

}

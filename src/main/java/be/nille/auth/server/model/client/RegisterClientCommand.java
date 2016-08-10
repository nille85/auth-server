/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.client;

import be.nille.auth.server.model.client.Client;
import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nholvoet
 */
@Setter
@Getter
public class RegisterClientCommand {
    
    private String applicationName;
    private Set<String> scopes;
    
    
    public RegisterClientCommand(){
        scopes = new HashSet<>();
    }
    
    public Client toClient(final String id){
        Client client = new Client(id);
        client.setApplicationName(applicationName);
        String scopeString = StringUtils.join(scopes.toArray(),",");
        client.setScopes(scopeString);
        return client;
    }

}

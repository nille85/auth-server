/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.client;


import be.nille.auth.server.model.client.RegisterClientCommand;
import be.nille.auth.server.model.client.Client;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;



/**
 * @author nholvoet
 */
public class RegisterClientCommandTest {

    @Test
    public void toClient(){
        RegisterClientCommand command = new RegisterClientCommand();
        command.setApplicationName("appName");
        Set<String> scopes = new TreeSet<>();
        scopes.add("read");
        scopes.add("write");
        command.setScopes(scopes);
        
        Client client = command.toClient("1235");
        assertEquals("read,write",client.getScopes());
        assertNotNull(client.getCredentials().getSecret());

        
    }
}

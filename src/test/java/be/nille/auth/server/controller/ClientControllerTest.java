/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.controller;

import be.nille.auth.server.controller.ClientController;
import be.nille.auth.server.controller.ErrorControllerAdvice;
import be.nille.auth.server.model.Client;
import be.nille.auth.server.model.ClientService;
import be.nille.auth.server.model.RegisterClientCommand;
import be.nille.jwt.aspect.JWTAspect;
import be.nille.jwt.aspect.PayloadService;
import be.nille.jwt.components.model.Payload;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author nholvoet
 */
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController controller;

    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AspectJProxyFactory factory = new AspectJProxyFactory(controller);
        PayloadService payloadService = Mockito.mock(PayloadService.class);
        Payload payload = Payload.builder()
                .withClaim("iss", "Nille")
                .withClaim("aud", "client1")
                .build();
        when(payloadService.verify()).thenReturn(payload);
        JWTAspect aspect = new JWTAspect(payloadService);
        factory.addAspect(aspect);
        controller = factory.getProxy();
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorControllerAdvice())
                .build();

        this.mapper = new ObjectMapper();
    }

    @Test
    public void getClients() throws Exception {
        List<Client> clients = new ArrayList<>();
        Client client = new Client("client1");
        client.setApplicationName("appName");
        client.setScopes("read,write");
        clients.add(client);
        when(clientService.getClients()).thenReturn(clients);

        this.mockMvc.perform(get("/clients"))
                .andExpect(jsonPath("$[0].credentials.clientId").value("client1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    
    @Test
    public void getClient() throws Exception {
          
        Client client = new Client("client1");
        client.setApplicationName("appName");
        client.setScopes("read,write");
      
        when(clientService.getClient(anyString())).thenReturn(client);

        this.mockMvc.perform(get("/clients/client1"))
                .andExpect(jsonPath("$.credentials.clientId").value("client1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    
    @Test
    public void getClientForbidden() throws Exception {
    
        /*
        Payload was constructed with client1 in setup
        */
        this.mockMvc.perform(get("/clients/client2"))
                
                .andExpect(status().isForbidden())
                .andDo(print());
    }
    
    
    @Test
    public void registerClient() throws Exception {
        RegisterClientCommand command = new RegisterClientCommand();
        command.setApplicationName("appName");
        Set<String> scopes = new TreeSet<>();
        scopes.add("read");
        scopes.add("write");
        command.setScopes(scopes);
        
        Client client = new Client("client1");
        client.setApplicationName("appName");
        client.setScopes("read,write");
        
      
        when(clientService.register(any(RegisterClientCommand.class))).thenReturn(client);
      
        this.mockMvc.perform(post("/clients")
                .header("Content-Type", "application/json")
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.credentials.clientId").isNotEmpty())
                .andExpect(jsonPath("$.credentials.secret").isNotEmpty())
                .andDo(print());
    }

}

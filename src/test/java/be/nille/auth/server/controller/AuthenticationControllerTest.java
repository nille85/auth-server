/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.controller;

import be.nille.auth.server.controller.AuthenticationController;
import be.nille.auth.server.controller.ErrorControllerAdvice;
import be.nille.auth.server.model.InvalidCredentialsException;
import be.nille.auth.server.model.client.Credentials;
import be.nille.auth.server.model.client.Client;
import be.nille.auth.server.model.client.ClientRepository;
import be.nille.auth.server.model.client.ClientService;
import be.nille.jwt.components.model.JWT;
import be.nille.jwt.components.model.Payload;
import be.nille.jwt.components.signer.JWTSigner;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author nholvoet
 */
public class AuthenticationControllerTest {

    
    @Mock
    private ClientService service;
    
    @Mock 
    private JWTSigner jwtSigner;
    
    @InjectMocks
    private AuthenticationController controller;
    
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setup() {  
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)            
                .setControllerAdvice(new ErrorControllerAdvice())     
                .build();
        mapper = new ObjectMapper();
    }
    
    @Test
    public void authenticate() throws Exception{
        
        
        Client client = new Client("clientid");
        client.setApplicationName("appName");
        client.setScopes("read,write");
        
        Mockito.when(service.authenticate(any(Credentials.class))).thenReturn(client);
        Mockito.when(jwtSigner.sign(any(Payload.class))).thenReturn(new JWT("jwtvalue"));
        this.mockMvc.perform(post("/auth/token")
                .header("Content-Type", "application/json")
                .content(mapper.writeValueAsString(client.getCredentials())))
                .andExpect(status().isOk())
                .andExpect(header().string("X-AUTH", "jwtvalue"))
                .andDo(print());
    }
    
    @Test
    public void authenticateInvalidCredentials() throws Exception{
        Client client = new Client("clientid");
        client.setApplicationName("appName");
        client.setScopes("read,write");
          
        Mockito.when(service.authenticate(any(Credentials.class))).thenThrow(InvalidCredentialsException.class);
      
        this.mockMvc.perform(post("/auth/token")
                .header("Content-Type", "application/json")
                .content(mapper.writeValueAsString(client.getCredentials())))
                .andExpect(status().isUnauthorized())
                .andExpect(header().string("X-AUTH", nullValue()))
                .andDo(print());
    }  
     
}

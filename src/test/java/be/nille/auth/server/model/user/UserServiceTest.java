/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.user;

import be.nille.auth.server.model.InvalidCredentialsException;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 * @author nholvoet
 */
public class UserServiceTest {
    
    @Mock
    private UserRepository repository;
    
    @InjectMocks
    private UserService userService;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void authenticate(){
        when(repository.findByEmailAndPassword(anyString(), anyString())).thenReturn(new User());
        assertNotNull(userService.authenticate("email", "password"));
    }
    
    @Test(expected = InvalidCredentialsException.class)
    public void authenticateWithInvalidCredentials(){
        when(repository.findByEmailAndPassword(anyString(), anyString())).thenReturn(null);
        userService.authenticate("email", "password");
    }

}

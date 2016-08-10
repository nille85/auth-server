/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.user;

import be.nille.auth.server.model.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nholvoet
 */
@Service
public class UserService {
    
    
    private final UserRepository repository;
    
    @Autowired
    public UserService(final UserRepository repository){
        this.repository = repository;
    }
    
    
    public User authenticate(final String email, final String password){
        User user = repository.findByEmailAndPassword(email, password);
        if(user == null){
            throw new InvalidCredentialsException("The user has invalid credentials");
        }
        return user;
    }
    
    

}

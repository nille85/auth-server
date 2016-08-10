/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.user;

import be.nille.auth.server.H2MemoryDatabaseConfig;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author nholvoet
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = H2MemoryDatabaseConfig.class)
@Transactional
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository repository;
    
    
    @Test
    public void findByEmailAndPassword(){
        User user = new User();
        user.setEmail("user@test.be");
        user.setPassword("password");
        user.setRole("admin");
        repository.save(user);
        user = repository.findByEmailAndPassword("user@test.be", "password");
        assertNotNull(user);
        repository.deleteAll();
    }
    
    @Test
    public void findByEmailAndPasswordNotFound(){
        User user = repository.findByEmailAndPassword("unexisting@test.be", "password");
        assertNull(user);
    }

}

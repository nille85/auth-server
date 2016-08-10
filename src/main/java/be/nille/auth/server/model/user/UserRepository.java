/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author nholvoet
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
   
    User findByEmailAndPassword(String email,String password);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nholvoet
 */
@Entity
@Table(name = "USER")
@Getter
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    
    @Setter
    @Column(name = "EMAIL")
    private String email;  
   
    @Setter
    @Column(name = "PASSWORD")
    private String password;
    
    @Setter
    @Column(name = "ROLE")
    private String role;
    
    @OneToMany(mappedBy = "user")
    private final List<Token> tokens;
    
    public User(){
        tokens = new ArrayList<>();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model.client;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nholvoet
 */
@Entity
@Table(name = "CLIENT")
@Getter
public class Client implements Serializable {

    public Client(final String clientId){
        this.credentials = new Credentials(clientId);   
    }
    
    protected Client(){}
    
    
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;
    
    @Embedded
    private Credentials credentials;
       

    @Setter
    @Column(name = "APPLICATION_NAME")
    private String applicationName;  
   
    @Setter
    @Column(name = "SCOPES")
    private String scopes;
    
    @Setter
    @Column(name = "resources")
    private String resources;
    
  
    
}

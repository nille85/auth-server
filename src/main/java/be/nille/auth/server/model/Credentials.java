/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model;

import be.nille.auth.server.cryptography.SecureStringGenerator;
import be.nille.auth.server.cryptography.StringGenerator;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * @author nholvoet
 */
@Data
@Embeddable
public class Credentials implements Serializable {
    
    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "SECRET")
    private String secret;
    
    public Credentials(){}
    
    public Credentials(final String clientId){
        StringGenerator generator = new SecureStringGenerator(20);
        this.clientId = clientId;
        this.secret = generator.generate();
    }

}

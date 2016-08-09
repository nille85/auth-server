/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server;

import be.nille.jwt.aspect.JWTAspect;
import be.nille.jwt.aspect.PayloadService;


import be.nille.jwt.components.signer.JWTSigner;
import be.nille.jwt.components.signer.JWTSecretKeySigner;
import be.nille.jwt.components.verifier.JWTSecretKeyVerifier;
import be.nille.jwt.components.verifier.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author nholvoet
 */
@Configuration
@PropertySource("file:${config.home}/application.properties")
public class JWTConfig {
    
   @Autowired
   private Environment environment;
    
    @Bean
    public JWTSigner signer(){
        JWTSigner signer = new JWTSecretKeySigner(environment.getProperty("jwt.secret"));
        return signer;
    }
    
    @Bean
    public JWTVerifier verifier(){
        JWTVerifier verifier = new JWTSecretKeyVerifier(environment.getProperty("jwt.secret"));
        return verifier;
    }
    
    
    
    @Bean
    public PayloadService payloadService() {
        return new PayloadRequestService(verifier());
    }

    @Bean
    public JWTAspect jwtAspect() {
        JWTAspect aspect = new JWTAspect(payloadService());
        return aspect;
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server;

import be.nille.jwt.aspect.PayloadService;
import be.nille.jwt.components.model.JWT;
import be.nille.jwt.components.verifier.JWTVerifier;
import be.nille.jwt.components.model.Payload;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author nholvoet
 */
public class PayloadRequestService implements PayloadService {
    
    private final JWTVerifier verifier;
    
    public PayloadRequestService(final JWTVerifier verifier){
        this.verifier = verifier;
    }
  

    @Override
    public Payload verify() {
        ServletRequestAttributes t = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = t.getRequest();
     
        final String jwtValue = request.getHeader("X-AUTH");
        JWT jwt = new JWT(jwtValue);
        Payload payload =verifier.verify(jwt);
       
        return payload;
    }

}

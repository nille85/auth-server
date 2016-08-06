/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author nholvoet
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket version1Docket() {
        final Set<String> produces = new HashSet<>();

        produces.add("application/json");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("version 1")
                .produces(produces);
    }
    
    @Bean
    SecurityConfiguration security() {        
        return new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER, "x-auth", ",");
    }

}

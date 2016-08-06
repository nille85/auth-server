/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.controller;

import be.nille.auth.server.model.InvalidCredentialsException;
import be.nille.jwt.aspect.AuthenticationException;
import be.nille.jwt.aspect.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author nholvoet
 */
@ControllerAdvice
public class ErrorControllerAdvice {

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public @ResponseBody  void invalidCredentialsException(final InvalidCredentialsException ex) {
        
    }
    
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public @ResponseBody  void authenticationException(final AuthenticationException ex) {
        
    }
    
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public @ResponseBody  void forbiddenException(final AuthorizationException ex) {
        
    }
}

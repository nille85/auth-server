/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.model;

/**
 * @author nholvoet
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(final String message) {
        super(message);
    }
}

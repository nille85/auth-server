/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.cryptography;

import java.util.Random;

/**
 * @author nholvoet
 */
/*
This StringGenerator is fast but unsecure, 
It only uses the java Random function which is a pseudo random function  
It can be hacked and predicted because it's a pure mathematical function
*/
public class UnsecureStringGenerator implements StringGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private final int length;

    private final Random random;
    private final char[] chars;

    public UnsecureStringGenerator(final int length) {
        random = new Random();
        chars = CHARACTERS.toCharArray();
        this.length = length;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();

    }

}

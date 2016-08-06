/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nille.auth.server.cryptography;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author nholvoet
 */
/*
*The set of possibe generated characters is 26 + 10 = 36
*we only nee 6 entropy bits in order to get a fully random alphanumeric character 2^6 = 64 > 36

If we would use SecureRandom.nextInt() to generate every character we would use up 32 entropy bits
*/
public class SecureStringGenerator implements StringGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private final int length;

    public SecureStringGenerator(final int length) {
        this.length = length;
    }

    /*
    We run out of entropy bits when 64/12 > log2(36) (= +-5.16..)
    So after generating 12 characters we have to reseed
    */
    @Override
    public String generate() {
        SecureRandom srand = new SecureRandom();
        Random rand = new Random();
        char[] buff = new char[length];

        for (int i = 0; i < length; ++i) {
            // reseed rand once you've used up all available entropy bits
            if ((i % 12) == 0) {
                rand.setSeed(srand.nextLong()); // 64 bits of random
            }
            char[] characterArray = CHARACTERS.toCharArray();
            /*every call to rand.nextInt will consume 6 bits of entropy 
            */
            buff[i] = characterArray[rand.nextInt(characterArray.length)];
        }
        return new String(buff);
    }

}

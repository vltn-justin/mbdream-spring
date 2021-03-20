package com.chamalo.mbdream.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class to encode a password with a generated salt
 *
 * @author Chamalo
 */
public class PasswordEncoderJava {

    /**
     * Method to generate a salt
     *
     * @return salt generated
     */
    public byte[] generateSalt () {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Method to generate an encoded password
     *
     * @param password Password to encode
     * @param salt     Salt for encoding
     *
     * @return Encoded password
     */
    public byte[] generatePassword (String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}

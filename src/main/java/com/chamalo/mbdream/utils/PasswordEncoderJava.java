package com.chamalo.mbdream.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoderJava.class);

    /**
     * Method to generate a salt
     *
     * @return salt generated
     */
    public byte[] generateSalt() {
        var secureRandom = new SecureRandom();
        var salt = new byte[16];
        secureRandom.nextBytes(salt);
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
    public byte[] generatePassword(String password, byte[] salt) {
        try {
            var messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Error generating password : ", e);
        }
        return new byte[0];
    }
}

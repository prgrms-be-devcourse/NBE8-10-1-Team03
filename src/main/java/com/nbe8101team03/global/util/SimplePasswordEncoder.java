package com.nbe8101team03.global.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public final class SimplePasswordEncoder {
    private static final int SALT_BYTES = 16;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256; // bits
    private static final String ALGO = "PBKDF2WithHmacSHA256";

    private SimplePasswordEncoder() {}

    public static String encode(String rawPassword) {
        try {
            byte[] salt = new byte[SALT_BYTES];
            SecureRandom.getInstanceStrong().nextBytes(salt);

            byte[] hash = pbkdf2(rawPassword, salt, ITERATIONS, KEY_LENGTH);

            String saltB64 = Base64.getEncoder().encodeToString(salt);
            String hashB64 = Base64.getEncoder().encodeToString(hash);

            return ITERATIONS + ":" + saltB64 + ":" + hashB64;
        } catch (Exception e) {
            throw new IllegalStateException("Password encoding failed", e);
        }
    }

    public static boolean matches(String rawPassword, String stored) {
        try {
            String[] parts = stored.split(":");
            if (parts.length != 3) return false;

            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[2]);

            byte[] actualHash = pbkdf2(rawPassword, salt, iterations, expectedHash.length * 8);

            return constantTimeEquals(expectedHash, actualHash);
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf2(String password, byte[] salt, int iterations, int keyLengthBits) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLengthBits);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGO);
        return factory.generateSecret(spec).getEncoded();
    }

    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) return false;
        int result = 0;
        for (int i = 0; i < a.length; i++) result |= (a[i] ^ b[i]);
        return result == 0;
    }
}
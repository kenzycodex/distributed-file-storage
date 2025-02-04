package com.cloudstore.service.file;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EncryptionService {
    // AES Symmetric Encryption for File Chunks
    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 256-bit key
        return keyGen.generateKey();
    }

    public static String encryptAES(byte[] data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static byte[] decryptAES(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return decryptedBytes;
    }

    // RSA Asymmetric Encryption for Key Management
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // 2048-bit key
        return keyPairGenerator.generateKeyPair();
    }

    public static String encryptRSA(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptRSA(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    // Securely store and retrieve encryption keys
    public static class KeyManager {
        // In a real-world scenario, this would interact with a secure key management system
        private static final Map<String, SecretKey> aesKeys = new ConcurrentHashMap<>();
        private static final Map<String, KeyPair> rsaKeyPairs = new ConcurrentHashMap<>();

        public static void storeAESKey(String fileId, SecretKey key) {
            aesKeys.put(fileId, key);
        }

        public static SecretKey retrieveAESKey(String fileId) {
            return aesKeys.get(fileId);
        }

        public static void storeRSAKeyPair(String userId, KeyPair keyPair) {
            rsaKeyPairs.put(userId, keyPair);
        }

        public static KeyPair retrieveRSAKeyPair(String userId) {
            return rsaKeyPairs.get(userId);
        }
    }
}
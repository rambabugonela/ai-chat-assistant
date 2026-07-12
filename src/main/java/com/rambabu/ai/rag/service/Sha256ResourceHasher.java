package com.rambabu.ai.rag.service;

import com.rambabu.ai.exception.AiServiceException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.rambabu.ai.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Component
public class Sha256ResourceHasher implements ResourceHasher {

    private static final int BUFFER_SIZE = 8 * 1024;
    @Override
    public String calculateHash(Resource resource) {

        byte[] hashBytes;
        try (InputStream inputStream = resource.getInputStream()) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
           hashBytes =  digest.digest();

        } catch (IOException | NoSuchAlgorithmException e) {
            throw new AiServiceException(INTERNAL_SERVER_ERROR, e);
        }

        return bytesToHex(hashBytes);
    }
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}

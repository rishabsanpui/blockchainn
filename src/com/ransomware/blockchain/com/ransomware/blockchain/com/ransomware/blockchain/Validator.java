package com.ransomware.blockchain;

import java.security.*;

public class Validator {
    private String id;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Validator(String id) throws NoSuchAlgorithmException {
        this.id = id;
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    public String getId() { return id; }
    public PrivateKey getPrivateKey() { return privateKey; }
    public PublicKey getPublicKey() { return publicKey; }
}

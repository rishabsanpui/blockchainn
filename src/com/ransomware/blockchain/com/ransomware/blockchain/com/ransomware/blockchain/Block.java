package com.ransomware.blockchain;

import java.security.PublicKey;

public class Block {
    private String fileHash;
    private String previousHash;
    private String signature;
    private String validatorId;
    private long timestamp;

    public Block(String fileHash, String previousHash, Validator validator) throws Exception {
        this.fileHash = fileHash;
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.validatorId = validator.getId();

        // Sign block
        String dataToSign = fileHash + previousHash + timestamp + validatorId;
        this.signature = DigitalSignatureUtils.signData(dataToSign, validator.getPrivateKey());
    }

    public String getFileHash() { return fileHash; }
    public String getPreviousHash() { return previousHash; }
    public String getSignature() { return signature; }
    public String getValidatorId() { return validatorId; }
    public long getTimestamp() { return timestamp; }

    // Verify block signature
    public boolean isValidBlock(PublicKey validatorPublicKey) throws Exception {
        String dataToVerify = fileHash + previousHash + timestamp + validatorId;
        return DigitalSignatureUtils.verifySignature(dataToVerify, signature, validatorPublicKey);
    }
}

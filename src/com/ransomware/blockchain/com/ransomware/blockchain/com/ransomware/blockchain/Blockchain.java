package com.ransomware.blockchain;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Blockchain {
    private List<Block> chain;
    private HashMap<String, PublicKey> validators;

    public Blockchain() {
        chain = new ArrayList<>();
        validators = new HashMap<>();

        try {
            // Create Genesis block (first block)
            chain.add(new Block("Genesis File Hash", "0", new Validator("Genesis Validator")));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Genesis block", e);
        }
    }

    // Register a new validator (authority)
    public void addValidator(Validator validator) {
        validators.put(validator.getId(), validator.getPublicKey());
    }

    // Add a new block (only if validator is authorized)
    public boolean addBlock(String fileHash, Validator validator) throws Exception {
        if (!validators.containsKey(validator.getId())) {
            System.out.println("Unauthorized Validator: " + validator.getId());
            return false;
        }

        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(fileHash, previousBlock.getFileHash(), validator);

        // Verify signature before adding
        PublicKey validatorPublicKey = validators.get(validator.getId());
        if (newBlock.isValidBlock(validatorPublicKey)) {
            chain.add(newBlock);
            System.out.println("Block added by Validator: " + validator.getId());
            return true;
        } else {
            System.out.println("Invalid Signature! Block Rejected.");
            return false;
        }
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("\nBlock:");
            System.out.println("File Hash: " + block.getFileHash());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Validator ID: " + block.getValidatorId());
            System.out.println("Signature: " + block.getSignature());
        }
    }
}

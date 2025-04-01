package com.ransomware.blockchain;

public class PoABlockchainTest {
    public static void main(String[] args) throws Exception {
        // Create Blockchain
        Blockchain blockchain = new Blockchain();

        // Create Validators
        Validator validator1 = new Validator("Validator1");
        Validator validator2 = new Validator("Validator2");

        // Register Validators
        blockchain.addValidator(validator1);
        blockchain.addValidator(validator2);

        // Add Blocks
        blockchain.addBlock("FileHash1", validator1);
        blockchain.addBlock("FileHash2", validator2);

        // Unauthorized Validator trying to add a block
        Validator fakeValidator = new Validator("FakeValidator");
        blockchain.addBlock("FakeFileHash", fakeValidator);

        // Print Blockchain
        blockchain.printBlockchain();
    }
}

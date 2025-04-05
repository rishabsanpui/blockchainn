package com.ransomware.blockchain;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

import java.io.File;
import java.util.List;

public class SecureFileStorageTest {
    public static void main(String[] args) throws Exception {
        // Step 1: Connect to IPFS
        IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

        // Step 2: Upload file to IPFS
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("sample.txt"));
        List<MerkleNode> result = ipfs.add(file);
        String cid = result.get(0).hash.toString();

        System.out.println("✅ Uploaded to IPFS with CID: " + cid);

        // Step 3: Use CID as file hash in blockchain
        SecureFileLedger ledger = new SecureFileLedger();

        Validator validator1 = new Validator("Validator1");
        Validator validator2 = new Validator("Validator2");

        ledger.addValidator(validator1);
        ledger.addValidator(validator2);

        ledger.addBlock(cid, validator1);
        ledger.addBlock("another-sha256-hash", validator2);  // Or upload another file if you want

        Validator fakeValidator = new Validator("FakeValidator");
        ledger.addBlock("FakeFileHash", fakeValidator);

        ledger.printBlockchain();
    }
}

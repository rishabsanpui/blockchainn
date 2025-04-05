package com.ransomware.blockchain;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Base64;

public class IPFSService {
    private IPFS ipfs;

    public IPFSService() {
        this.ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001"); // IPFS daemon must be running
    }

    public String uploadFile(String filePath) throws Exception {
        File file = new File(filePath);
        NamedStreamable.FileWrapper fileWrapper = new NamedStreamable.FileWrapper(file);
        MerkleNode response = ipfs.add(fileWrapper).get(0);
        System.out.println("File uploaded to IPFS. CID: " + response.hash);
        return response.hash.toString();
    }

    public void downloadFile(String cid, String outputPath) throws Exception {
        Multihash filePointer = Multihash.fromBase58(cid);
        byte[] fileData = ipfs.cat(filePointer);
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(fileData);
        }
        System.out.println("File downloaded from IPFS to: " + outputPath);
    }

    public String calculateFileHash(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        byte[] hashBytes = digest.digest(fileBytes);
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    public boolean verifyFile(String originalPath, String downloadedPath) throws Exception {
        File originalFile = new File(originalPath);
        File downloadedFile = new File(downloadedPath);
        return calculateFileHash(originalFile).equals(calculateFileHash(downloadedFile));
    }
}

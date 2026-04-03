package com.dhatvibs.modules.service;

import com.azure.storage.blob.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AzureBlobService {

    private final BlobServiceClient blobServiceClient;

    public String uploadFile(MultipartFile file, String containerName) throws IOException {

        BlobContainerClient containerClient =
                blobServiceClient.getBlobContainerClient(containerName);

        if (!containerClient.exists()) {
            containerClient.create();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        BlobClient blobClient = containerClient.getBlobClient(fileName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }

    public void deleteFile(String fileUrl, String containerName) {
        try {
            BlobContainerClient containerClient =
                    blobServiceClient.getBlobContainerClient(containerName);

            String blobName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            containerClient.getBlobClient(blobName).delete();

        } catch (Exception e) {
            throw new RuntimeException("Delete failed: " + e.getMessage());
        }
    }
}
package com.example.demo_3_bai_toan.DocumentModule.Service.documentService;

import com.example.demo_3_bai_toan.DocumentModule.Entity.documentEntity;
import com.example.demo_3_bai_toan.DocumentModule.Entity.documentRequest;
import com.example.demo_3_bai_toan.DocumentModule.Entity.documentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public interface documentService {

//     String uploadFile(MultipartFile file);

     documentResponse createFile(MultipartFile file , long parentId);

     File createZipFile(Long vuAnId) throws IOException;

     documentResponse getDocument(long documentId);

     documentResponse moveDocument(long documentId , long documentParentId);

     documentResponse deleteDocument(long documentId);

     documentResponse updateDocument(long documentId, String name);

     documentEntity createFolder(long parentId, documentRequest newDocument) throws Exception;


}

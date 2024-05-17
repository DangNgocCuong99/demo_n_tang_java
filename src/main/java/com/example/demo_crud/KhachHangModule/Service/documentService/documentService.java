package com.example.demo_crud.KhachHangModule.Service.documentService;

import com.example.demo_crud.KhachHangModule.Entity.documentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public interface documentService {

//     String uploadFile(MultipartFile file);

     documentResponse createFile(MultipartFile file , long parentId);

     File createZipFile() throws IOException;

     documentResponse getDocument(long documentId);

     documentResponse moveDocument(long documentId , long documentParentId);

     documentResponse deleteDocument(long documentId);

     documentResponse updateDocument(long documentId, String name);

}

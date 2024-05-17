package com.example.demo_crud.KhachHangModule.Service.documentService;

import com.example.demo_crud.KhachHangModule.Entity.CustomResponse.customResponse;
import com.example.demo_crud.KhachHangModule.Entity.documentEntity;
import com.example.demo_crud.KhachHangModule.Entity.documentResponse;
import com.example.demo_crud.KhachHangModule.Interface.DocumentInterface;
import com.example.demo_crud.KhachHangModule.repository.documentRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Component
public class impDocument implements documentService {
    @Autowired
    private documentRepository documentRepository;

    private static final String UPLOAD_DIR = "uploads";
    private static final String ZIP_FILE_NAME = "va1.zip";

    @PostConstruct
    public static void init() {
        String currentDir = System.getProperty("user.dir");
        String uploadPath = currentDir + File.separator + UPLOAD_DIR;

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public customResponse uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return new customResponse("Please select a file to upload.");
        }

        try {
            // Lấy tên tệp tin gốc
            String originalFileName = file.getOriginalFilename();

            // Lấy timestamp hiện tại
            long timestamp = System.currentTimeMillis();

            // Tạo tên tệp tin mới bao gồm timestamp
            String newFileName = timestamp + "_" + originalFileName;

            // Tạo đường dẫn lưu tệp tin trên server
            String uploadPath = UPLOAD_DIR + File.separator + newFileName;

            // Lưu tệp tin vào thư mục upload
            Files.copy(file.getInputStream(), Path.of(uploadPath), StandardCopyOption.REPLACE_EXISTING);

            // Tạo URL để truy cập tệp tin
            String fileUrl = "/uploads/" + newFileName;

            return new customResponse("http://192.168.1.44:8082/document" + fileUrl);
        } catch (IOException e) {
            return new customResponse("Failed to upload file: " + e.getMessage());
        }
    }

    @Override
    public File createZipFile() throws IOException {
        String currentDir = System.getProperty("user.dir");
        String uploadPath = currentDir + File.separator + UPLOAD_DIR;

        File zipFile = new File(uploadPath + File.separator + ZIP_FILE_NAME);

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
            // Lấy danh sách các file ảnh từ repository hoặc từ đâu đó
            List<documentEntity> imageList = documentRepository.findAll();

            for (documentEntity image : imageList) {
                // Tạo entry trong file zip cho mỗi file ảnh
                zipOut.putNextEntry(new ZipEntry(image.getName()));

                // Đọc file ảnh và ghi dữ liệu vào file zip
                try (InputStream in = new FileInputStream(uploadPath + File.separator + image.getName())) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zipOut.write(buffer, 0, len);
                    }
                }

                // Đóng entry của file ảnh
                zipOut.closeEntry();
            }
        }

        return zipFile;
    }

    @Override
    public documentResponse getDocument(long documentId) {
        Optional<documentEntity> document = documentRepository.findById(documentId);
        System.out.println(document);
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentGeted = document.get();
        System.out.println(documentGeted);

        Long parentDocumentId = null;
        if (documentGeted.getParentDocument() != null) {
            parentDocumentId = documentGeted.getParentDocument().getId();
        }

        List<DocumentInterface> listChild = documentRepository.findByParentDocumentId(documentGeted.getId());

        return new documentResponse(documentGeted.getId(), documentGeted.getName(), documentGeted.getType(), Optional.ofNullable(parentDocumentId), listChild);
    }

    @Override
    public documentResponse moveDocument(long documentId, long documentParentId) {
        Optional<documentEntity> document = documentRepository.findById(documentId);
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentGeted = document.get();
        System.out.println(documentGeted);

        Optional<documentEntity> documentParent = documentRepository.findById(documentParentId);
        if (documentParent.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentParentGeted = documentParent.get();
        System.out.println(documentParentGeted);

        documentGeted.setParentDocument(documentParentGeted);
        documentRepository.save(documentGeted);

        List<DocumentInterface> listChild = documentRepository.findByParentDocumentId(documentGeted.getId());

        return new documentResponse(documentGeted.getId(), documentGeted.getName(), documentGeted.getType(), Optional.ofNullable(documentGeted.getParentDocument().getId()), listChild);
    }

    @Override
    public documentResponse deleteDocument(long documentId) {
        if (documentId == 6) {
            throw new IllegalArgumentException("khong xoa root");
        }
        Optional<documentEntity> document = documentRepository.findById(documentId);
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentGeted = document.get();
        System.out.println(documentGeted);

        List<DocumentInterface> listChild = documentRepository.findByParentDocumentId(documentGeted.getId());

        return new documentResponse(documentGeted.getId(), documentGeted.getName(), documentGeted.getType(), Optional.ofNullable(documentGeted.getParentDocument().getId()), listChild);
    }

    @Override
    public documentResponse updateDocument(long documentId, String nameDocument) {
        Optional<documentEntity> document = documentRepository.findById(documentId);
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentGeted = document.get();
        System.out.println(documentGeted);

        documentGeted.setName(nameDocument);
        documentRepository.save(documentGeted);

        List<DocumentInterface> listChild = documentRepository.findByParentDocumentId(documentGeted.getId());

        return new documentResponse(documentGeted.getId(), documentGeted.getName(), documentGeted.getType(), Optional.ofNullable(documentGeted.getParentDocument().getId()), listChild);
    }

}



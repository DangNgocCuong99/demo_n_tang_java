package com.example.demo_3_bai_toan.DocumentModule.Service.documentService;

import com.example.demo_3_bai_toan.DocumentModule.Entity.documentEntity;
import com.example.demo_3_bai_toan.DocumentModule.Entity.documentResponse;
import com.example.demo_3_bai_toan.DocumentModule.Interface.DocumentInterface;
import com.example.demo_3_bai_toan.DocumentModule.Entity.documentRequest;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnEntity;
import com.example.demo_3_bai_toan.DocumentModule.Repository.documentRepository;
import com.example.demo_3_bai_toan.VuAnModule.Repository.vuAnRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Component
public class impDocument implements documentService {
    @Value("${bezkoder.openapi.dev-url}")
    private String devUrl;
    private final vuAnRepository vuAnRepository;
    private final documentRepository documentRepository;
    @Autowired
    public impDocument(vuAnRepository vuAnRepository, documentRepository documentRepository) {
        this.vuAnRepository = vuAnRepository;
        this.documentRepository = documentRepository;
    }

    private static final String UPLOAD_DIR = "uploads";
    private static final String ZIP_FILE_NAME = "va1.zip";



    @PostConstruct
    public static void init() {
        String currentDir = System.getProperty("user.dir");
        String uploadPath = currentDir + File.separator + UPLOAD_DIR;

        File dir = new File(uploadPath);
        if (!dir.exists()) {
            boolean dirCreated = dir.mkdirs();
            if (!dirCreated) {
                // Xử lý lỗi khi không thể tạo thư mục
                System.err.println("Failed to create upload directory: " + uploadPath);
            }
        }
    }

    private String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please select a file to upload.");
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
//            String fileUrl = "/file/" + newFileName;
//            return new customResponse("http://192.168.1.50:8082/document" + fileUrl);

            return newFileName;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to upload file: " + e.getMessage());
        }
    }

    @Override
    public File createZipFile(Long vuAnId) throws IOException {
        String currentDir = System.getProperty("user.dir");
        String uploadPath = currentDir + File.separator + UPLOAD_DIR;

        File zipFile = new File(uploadPath + File.separator + ZIP_FILE_NAME);

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
            // Lấy danh sách các file ảnh từ repository hoặc từ đâu đó
            List<DocumentInterface> imageList = documentRepository.findByVuAnIdAndType(vuAnId,"file");

            for (DocumentInterface image : imageList) {
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

        return new documentResponse(documentGeted.getId(), documentGeted.getName(), documentGeted.getType(), Optional.ofNullable(parentDocumentId),null, listChild);
    }

    @Override
    public documentResponse moveDocument(long documentId, long documentParentId) {
        Optional<documentEntity> document = documentRepository.findById(documentId);
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentGot = document.get();
        System.out.println(documentGot);

        Optional<documentEntity> documentParent = documentRepository.findById(documentParentId);
        if (documentParent.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentParentGot = documentParent.get();
        System.out.println(documentParentGot);

        documentGot.setParentDocument(documentParentGot);
        documentRepository.save(documentGot);

        List<DocumentInterface> listChild = documentRepository.findByParentDocumentId(documentGot.getId());

        return new documentResponse(documentGot.getId(), documentGot.getName(), documentGot.getType(), Optional.ofNullable(documentGot.getParentDocument().getId()), null,listChild);
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
        documentEntity documentGot = document.get();
        System.out.println(documentGot);
        documentRepository.deleteById(documentGot.getId());

        List<DocumentInterface> listChild = documentRepository.findByParentDocumentId(documentGot.getId());

        return new documentResponse(documentGot.getId(), documentGot.getName(), documentGot.getType(), Optional.ofNullable(documentGot.getParentDocument().getId()), null,listChild);
    }

    @Override
    public documentResponse updateDocument(long documentId, String nameDocument) {
        Optional<documentEntity> document = documentRepository.findById(documentId);
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentGot = document.get();
        System.out.println(documentGot);

        documentGot.setName(nameDocument);
        documentRepository.save(documentGot);

        List<DocumentInterface> listChild = documentRepository.findByParentDocumentId(documentGot.getId());

        return new documentResponse(documentGot.getId(), documentGot.getName(), documentGot.getType(), Optional.ofNullable(documentGot.getParentDocument().getId()),null, listChild);
    }

    @Override
    public documentResponse createFile(MultipartFile file , long parentId){
        Optional<documentEntity> documentParent = documentRepository.findById(parentId);
        if (documentParent.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        documentEntity documentParentGot = documentParent.get();

        Optional<vuAnEntity> vuAnOptional = vuAnRepository.findById(documentParent.get().getVuAn().getId());
        if (vuAnOptional.isEmpty()) {
            throw new IllegalArgumentException("vu an khong ton tai");
        }

        String fileName = uploadFile(file);
        documentEntity documentNew = new documentEntity();
        documentNew.setVuAn(vuAnOptional.get());
        documentNew.setParentDocument(documentParentGot);
        documentNew.setName(fileName);
        documentNew.setType("file");
        documentNew.setUrl(devUrl+"/document/file/"+fileName);

        documentEntity documentSave = documentRepository.save(documentNew);

        return new documentResponse(documentSave.getId(),documentSave.getName(),documentSave.getType(), Optional.ofNullable(documentSave.getParentDocument().getId()), Optional.ofNullable(documentSave.getUrl()),new ArrayList<>());
    }

    @Override
    public documentEntity createFolder(long parentId, documentRequest newDocument) throws Exception {
        Optional<documentEntity> documentParent = documentRepository.findById(parentId);
        if(documentParent.isEmpty()){
            throw new Exception("Not Found parent");
        }

        documentEntity documentSave = new documentEntity();
        Optional<vuAnEntity> vuAnOptional = vuAnRepository.findById(documentParent.get().getVuAn().getId());
        if (vuAnOptional.isPresent()) {
            documentSave.setVuAn(vuAnOptional.get());
            documentSave.setParentDocument(documentParent.get());
            documentSave.setName(newDocument.getName());
            documentSave.setType(newDocument.getType());
            documentSave.setUrl(newDocument.getUrl());
            System.out.println(newDocument);
            return documentRepository.save(documentSave);
        } else {
            throw new Exception("VuAn not found with ID: " + documentParent.get().getVuAn().getId());
        }
    }

}



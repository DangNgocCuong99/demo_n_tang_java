package com.example.demo_crud.KhachHangModule.Controller;

import com.example.demo_crud.KhachHangModule.Entity.moveDocumentRequest;
import com.example.demo_crud.KhachHangModule.Entity.updateDocumentRequest;
import com.example.demo_crud.KhachHangModule.Service.documentService.documentService;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;

@Controller
@CrossOrigin
@RequestMapping("/document")
public class documentController {
    @Autowired
    private documentService documentService;

    private static final String ZIP_FILE_NAME = "va1.zip";

//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFiles(@RequestParam("file") MultipartFile file){
//        return new ResponseEntity<>(documentService.uploadFile(file), HttpStatus.OK);
//    }

    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Xây dựng đường dẫn tới tệp tin
            Path filePath = Path.of("uploads").resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/download")
    public void downloadImages(HttpServletResponse response) throws IOException {
        // Tạo file zip
        File zipFile = documentService.createZipFile();

        // Thiết lập thông tin response
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + ZIP_FILE_NAME + "\"");

        // Ghi nội dung file zip vào response
        try (OutputStream out = response.getOutputStream();
             FileInputStream fis = new FileInputStream(zipFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        }

        // Xóa file zip sau khi đã tải xuống
        zipFile.delete();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getById(@PathVariable Long id){
        return new ResponseEntity<>(documentService.getDocument(id),HttpStatus.OK);
    }

    @PutMapping("/move/{id}")
    public ResponseEntity<?> move(@PathVariable Long id,@RequestBody @NotNull moveDocumentRequest documentParent){
        return new ResponseEntity<>(documentService.moveDocument(id,documentParent.getParentId()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(documentService.deleteDocument(id),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody updateDocumentRequest document) {
        return new ResponseEntity<>(documentService.updateDocument(id,document.getName()), HttpStatus.OK);
    }

    @PostMapping("/create-file/{parentId}")
    public ResponseEntity<?> createFile(@PathVariable Long parentId,@RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(documentService.createFile(file,parentId), HttpStatus.OK);
    }

}


package com.example.demo_crud.KhachHangModule.Entity;

import com.example.demo_crud.KhachHangModule.Interface.DocumentInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class documentResponse {
    private Long id;
    private String name;
    private String type;
    private Optional<Long> parentId;
    private List<DocumentInterface> childDocuments;
}

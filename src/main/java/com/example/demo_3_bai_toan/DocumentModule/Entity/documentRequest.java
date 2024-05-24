package com.example.demo_3_bai_toan.DocumentModule.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class documentRequest {
    private String name;
    private String url;
    private String type;
    private Long parent_document_id;
}

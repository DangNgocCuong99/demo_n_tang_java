package com.example.demo_3_bai_toan.VuAnModule.Entity;

import com.example.demo_3_bai_toan.DocumentModule.Interface.DocumentOfflineInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class vuAnOffline {
    private Long id;
    private String name;
    private String description;
    private Long rootId;
    private List<DocumentOfflineInterface> documents;
}

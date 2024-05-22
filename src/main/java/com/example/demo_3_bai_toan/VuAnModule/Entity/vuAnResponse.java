package com.example.demo_3_bai_toan.VuAnModule.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class vuAnResponse {
    private Long id;
    private String name;
    private String description;
    private Long rootId;
}

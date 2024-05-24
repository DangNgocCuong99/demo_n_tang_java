package com.example.demo_3_bai_toan.UserModule.Entity.CustomResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class customResponse {
    String message ;
    public customResponse(String message) {
        this.message = message;
    }
}


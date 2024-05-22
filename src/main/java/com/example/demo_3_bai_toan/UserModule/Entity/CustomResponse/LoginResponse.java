package com.example.demo_3_bai_toan.UserModule.Entity.CustomResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
     String message;
     String token;

    public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

}

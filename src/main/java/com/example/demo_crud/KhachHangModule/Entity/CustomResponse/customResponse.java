package com.example.demo_crud.KhachHangModule.Entity.CustomResponse;

import lombok.AllArgsConstructor;
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


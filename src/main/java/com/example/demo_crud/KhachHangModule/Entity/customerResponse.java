package com.example.demo_crud.KhachHangModule.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class customerResponse {
    private Long id;
    private String username;
    private String password;
    private String private_key;

}

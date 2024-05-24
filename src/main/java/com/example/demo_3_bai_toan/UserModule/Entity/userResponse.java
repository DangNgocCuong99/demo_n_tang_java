package com.example.demo_3_bai_toan.UserModule.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userResponse {
    private Long id;
    private String username;
    private String password;
    private String private_key;

}

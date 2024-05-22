package com.example.demo_3_bai_toan.UserModule.Controller;


import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.customerRequest;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.updateKeyRequest;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomResponse.LoginResponse;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomResponse.customResponse;
import com.example.demo_3_bai_toan.UserModule.Entity.userEntity;
import com.example.demo_3_bai_toan.UserModule.Entity.userResponse;
import com.example.demo_3_bai_toan.UserModule.Service.UserService.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "User", description = "User management APIs")
@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;

    @GetMapping("/get")
    public ResponseEntity<List<userResponse>> getAll(){
        return new ResponseEntity<>(userService.getCustomer(),HttpStatus.OK);
    }

    @PostMapping("/post")
     public  ResponseEntity<userEntity> addCustomer(@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(userService.addCustomer(customerRequest),HttpStatus.CREATED);
    }

    @PutMapping("/put/{id}")
     public ResponseEntity<userEntity> updateCustomer(@PathVariable Long id,@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(userService.updateCustomer(id,customerRequest),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
     public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteCustomer(id),HttpStatus.OK);
    }

    @PostMapping("login")
    public  ResponseEntity<LoginResponse> login(@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(userService.login(customerRequest),HttpStatus.CREATED);
    }

    @PutMapping("/update-key/{id}")
    public ResponseEntity<customResponse> updateKey(@PathVariable Long id, @RequestBody updateKeyRequest key){
        return new ResponseEntity<>(userService.updateKey(id,key),HttpStatus.OK);
    }

}


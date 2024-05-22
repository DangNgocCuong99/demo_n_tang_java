package com.example.demo_3_bai_toan.UserModule.Controller;


import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.customerRequest;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.updateKeyRequest;
import com.example.demo_3_bai_toan.UserModule.Service.UserService.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>("hihi",HttpStatus.OK);
    }

    @PostMapping("/post")
     public  ResponseEntity<?> addCustomer(@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(userService.addCustomer(customerRequest),HttpStatus.CREATED);
    }

    @PutMapping("/put/{id}")
     public ResponseEntity<?> updateCustomer(@PathVariable Long id,@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(userService.updateCustomer(id,customerRequest),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
     public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteCustomer(id),HttpStatus.OK);
    }

    @PostMapping("login")
    public  ResponseEntity<?> login(@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(userService.login(customerRequest),HttpStatus.CREATED);
    }

    @PutMapping("/update-key/{id}")
    public ResponseEntity<?> updateKey(@PathVariable Long id,@RequestBody updateKeyRequest key){
        return new ResponseEntity<>(userService.updateKey(id,key),HttpStatus.OK);
    }

}


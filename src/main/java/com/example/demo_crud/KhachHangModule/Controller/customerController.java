package com.example.demo_crud.KhachHangModule.Controller;


import com.example.demo_crud.KhachHangModule.Entity.customerRequest;
import com.example.demo_crud.KhachHangModule.Entity.updateKeyRequest;
import com.example.demo_crud.KhachHangModule.Service.customerService.customerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class customerController {
    @Autowired
    private customerService customerService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>("hihi",HttpStatus.OK);
    }

    @PostMapping("/post")
     public  ResponseEntity<?> addCustomer(@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(customerService.addCustomer(customerRequest),HttpStatus.CREATED);
    }

    @PutMapping("/put/{id}")
     public ResponseEntity<?> updateCustomer(@PathVariable Long id,@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(customerService.updateCustomer(id,customerRequest),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
     public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return new ResponseEntity<>(customerService.deleteCustomer(id),HttpStatus.OK);
    }

    @PostMapping("login")
    public  ResponseEntity<?> login(@RequestBody customerRequest customerRequest){
        return new ResponseEntity<>(customerService.login(customerRequest),HttpStatus.CREATED);
    }

    @PutMapping("/update-key/{id}")
    public ResponseEntity<?> login(@PathVariable Long id,@RequestBody updateKeyRequest key){
        return new ResponseEntity<>(customerService.updateKey(id,key),HttpStatus.OK);
    }

}


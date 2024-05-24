package com.example.demo_3_bai_toan.UserModule.Service.UserService;

import com.example.demo_3_bai_toan.UserModule.Entity.CustomResponse.LoginResponse;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomResponse.customResponse;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.customerRequest;
import com.example.demo_3_bai_toan.UserModule.Entity.userEntity;
import com.example.demo_3_bai_toan.UserModule.Entity.userResponse;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.updateKeyRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface userService {
    List<userResponse> getCustomer();

    userEntity addCustomer(customerRequest customerRequest);

    userEntity updateCustomer(Long id, customerRequest customerRequest);

    customResponse deleteCustomer(Long id);

    LoginResponse login (customerRequest customerRequest);

    customResponse updateKey(Long id, updateKeyRequest key);

//    List<customerResponse> searchCustomer(String textSearch);
}

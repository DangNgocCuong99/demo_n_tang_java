package com.example.demo_crud.KhachHangModule.Service.customerService;

import com.example.demo_crud.KhachHangModule.Entity.CustomResponse.LoginResponse;
import com.example.demo_crud.KhachHangModule.Entity.CustomResponse.customResponse;
import com.example.demo_crud.KhachHangModule.Entity.customerRequest;
import com.example.demo_crud.KhachHangModule.Entity.customerResponse;
import com.example.demo_crud.KhachHangModule.Entity.updateKeyRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface customerService {
    List<customerResponse> getCustomer();

    customResponse addCustomer(customerRequest customerRequest);

    customResponse updateCustomer(Long id,customerRequest customerRequest);

    customResponse deleteCustomer(Long id);

    LoginResponse login (customerRequest customerRequest);

    customResponse updateKey(Long id, updateKeyRequest key);

//    List<customerResponse> searchCustomer(String textSearch);
}

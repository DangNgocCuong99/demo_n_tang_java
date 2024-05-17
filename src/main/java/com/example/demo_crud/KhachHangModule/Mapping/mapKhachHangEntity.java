package com.example.demo_crud.KhachHangModule.Mapping;


import com.example.demo_crud.KhachHangModule.Entity.customerEntity;
import com.example.demo_crud.KhachHangModule.Entity.customerRequest;
import com.example.demo_crud.KhachHangModule.Entity.customerResponse;
import lombok.Data;

import java.util.Optional;

@Data
public class mapKhachHangEntity {
    public static customerEntity mapResquestToEntity (customerRequest customerRequest){
        customerEntity customer = new customerEntity();
        customer.setUsername(customerRequest.getUsername());
//        customer.setPrivate_key(customerRequest.getPrivate_key());
        customer.setPassword((customerRequest.getPassword()));
        return customer;
    }

    public static customerResponse mapEntityToRespone(customerEntity customerEntity){
        customerResponse customerRespone = new customerResponse();
        customerRespone.setId(customerEntity.getId());
        customerRespone.setPassword(customerEntity.getPassword());
        customerRespone.setPrivate_key(customerEntity.getPrivate_key());
        customerRespone.setUsername(customerEntity.getUsername());
        return customerRespone;
    }

}
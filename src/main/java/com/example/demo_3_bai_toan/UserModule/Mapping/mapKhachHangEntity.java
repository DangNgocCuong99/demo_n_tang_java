package com.example.demo_3_bai_toan.UserModule.Mapping;


import com.example.demo_3_bai_toan.UserModule.Entity.userEntity;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.customerRequest;
import com.example.demo_3_bai_toan.UserModule.Entity.userResponse;
import lombok.Data;

@Data
public class mapKhachHangEntity {
    public static userEntity mapResquestToEntity (customerRequest customerRequest){
        userEntity customer = new userEntity();
        customer.setUsername(customerRequest.getUsername());
//        customer.setPrivate_key(customerRequest.getPrivate_key());
        customer.setPassword((customerRequest.getPassword()));
        return customer;
    }

    public static userResponse mapEntityToRespone(userEntity userEntity){
        userResponse customerRespone = new userResponse();
        customerRespone.setId(userEntity.getId());
        customerRespone.setPassword(userEntity.getPassword());
        customerRespone.setPrivate_key(userEntity.getPrivate_key());
        customerRespone.setUsername(userEntity.getUsername());
        return customerRespone;
    }

}
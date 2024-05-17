package com.example.demo_crud.KhachHangModule.Service.customerService;

import com.example.demo_crud.KhachHangModule.Entity.CustomResponse.LoginResponse;
import com.example.demo_crud.KhachHangModule.Entity.CustomResponse.customResponse;
import com.example.demo_crud.KhachHangModule.Entity.customerEntity;
import com.example.demo_crud.KhachHangModule.Entity.customerRequest;
import com.example.demo_crud.KhachHangModule.Entity.customerResponse;
import com.example.demo_crud.KhachHangModule.Entity.updateKeyRequest;
import com.example.demo_crud.KhachHangModule.Mapping.mapKhachHangEntity;
import com.example.demo_crud.KhachHangModule.repository.customerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class impCustomer implements customerService {
    private customerRepository customerRepository;

    @Autowired
    public void setCustomerRepository(customerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    private static final String private_key = "key_vks_9999999999999999999999999999999999999";

    @Override
    public List<customerResponse> getCustomer() {
        List<customerEntity> customerEntity = customerRepository.findAll();
        return customerEntity.stream().map(mapKhachHangEntity::mapEntityToRespone).collect(Collectors.toList());
    }

    @Override
    public customResponse addCustomer(customerRequest customerRequest) {
        customerEntity customerEntity = mapKhachHangEntity.mapResquestToEntity(customerRequest);
        customerRepository.save(customerEntity);
        return new customResponse("Thêm thành công");
    }

    @Override
    public customResponse updateCustomer(Long id, customerRequest customerRequest) {
        Optional<customerEntity> customerEntity = customerRepository.findById(id);
        if (customerEntity.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        customerEntity customerEntityNew = mapKhachHangEntity.mapResquestToEntity(customerRequest);
        customerEntityNew.setId(id);
        customerRepository.save(customerEntityNew);
        return new customResponse("Update thành công");
    }

    @Override
    public customResponse updateKey(Long id, updateKeyRequest key) {
        try {
            System.out.println("update key , id :" + id + ",key:" + key.getKey());
            Optional<customerEntity> userInfo = customerRepository.findById(id);
            if (userInfo.isEmpty()) {
                throw new IllegalArgumentException("Not Found");
            }
            customerEntity customerEntityNew = userInfo.get();
            customerEntityNew.setPrivate_key(key.getKey());
            customerRepository.save(customerEntityNew);
            return new customResponse("Update thanh cong");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new customResponse("Update that bai");
        }

    }

    @Override
    public customResponse deleteCustomer(Long id) {
        customerEntity customerEntity = new customerEntity();
        customerEntity.setId(id);
        customerRepository.delete(customerEntity);
        return new customResponse("Xoa thanh cong");
    }

    @Override
    public LoginResponse login(customerRequest userInfo) {
        try {
            customerEntity user = customerRepository.findByUsernameAndPassword(userInfo.getUsername(), userInfo.getPassword());
            String token = generateToken(user);
            return new LoginResponse("Đăng nhập thành công", token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new LoginResponse("Đăng nhập thất bại", null);
        }
    }

    public String generateToken(customerEntity userDetails) {
        System.out.println(userDetails.toString() + "login");
        final long jwtExpirationInMs = 7; //7 day
        SecretKey key = Keys.hmacShaKeyFor(private_key.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.claims()
                .setSubject(userDetails.getUsername());
        claims.put("id", userDetails.getId());
        claims.put("username", userDetails.getUsername());
        claims.put("password", userDetails.getPassword());
        claims.put("privateKey", userDetails.getPrivate_key());

        //                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact(); // old
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))

                .setExpiration(new Date(System.currentTimeMillis() + (jwtExpirationInMs * 1000 * 60 * 24)))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

//    public Claims parseToken(String token) {
//        SecretKey key = Keys.hmacShaKeyFor(private_key.getBytes());
//        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//        return claimsJws.getBody();
//    }

//    @Override
//     public List<customerResponse> searchCustomer(String textSearch){
//        customerEntity customerEntity = new customerEntity();
//        customerEntity.setTenkh(textSearch);
//        customerRepository.fin
//    }
}

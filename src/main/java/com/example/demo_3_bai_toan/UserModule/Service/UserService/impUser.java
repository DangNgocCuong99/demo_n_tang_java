package com.example.demo_3_bai_toan.UserModule.Service.UserService;

import com.example.demo_3_bai_toan.UserModule.Entity.CustomResponse.LoginResponse;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomResponse.customResponse;
import com.example.demo_3_bai_toan.UserModule.Entity.userEntity;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.customerRequest;
import com.example.demo_3_bai_toan.UserModule.Entity.userResponse;
import com.example.demo_3_bai_toan.UserModule.Entity.CustomRequest.updateKeyRequest;
import com.example.demo_3_bai_toan.UserModule.Mapping.mapKhachHangEntity;
import com.example.demo_3_bai_toan.UserModule.Repository.userRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class impUser implements userService {
    private final userRepository userRepository;
    @Autowired
    public impUser(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final String private_key = "key_vks_9999999999999999999999999999999999999";



    @Override
    public List<userResponse> getCustomer() {
        List<userEntity> userEntity = userRepository.findAll();
        return userEntity.stream().map(mapKhachHangEntity::mapEntityToRespone).collect(Collectors.toList());
    }

    @Override
    public userEntity addCustomer(customerRequest customerRequest) {
        userEntity userEntity = mapKhachHangEntity.mapResquestToEntity(customerRequest);
        return userRepository.save(userEntity);
    }

    @Override
    public userEntity updateCustomer(Long id, customerRequest customerRequest) {
        Optional<userEntity> customerEntity = userRepository.findById(id);
        if (customerEntity.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        userEntity userEntityNew = mapKhachHangEntity.mapResquestToEntity(customerRequest);
        userEntityNew.setId(id);
        return  userRepository.save(userEntityNew);
    }

    @Override
    public customResponse updateKey(Long id, updateKeyRequest key) {
        try {
            System.out.println("update key , id :" + id + ",key:" + key.getPrivate_key());
            Optional<userEntity> userInfo = userRepository.findById(id);
            if (userInfo.isEmpty()) {
                throw new IllegalArgumentException("Not Found");
            }
            userEntity userEntityNew = userInfo.get();
            userEntityNew.setPrivate_key(key.getPrivate_key());
            userRepository.save(userEntityNew);
            return new customResponse("Update thanh cong , key:" + key);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new customResponse("Update that bai");
        }

    }

    @Override
    public customResponse deleteCustomer(Long id) {
        userEntity userEntity = new userEntity();
        userEntity.setId(id);
        userRepository.delete(userEntity);
        return new customResponse("Xoa thanh cong");
    }

    @Override
    public LoginResponse login(customerRequest userInfo) {
        try {
            userEntity user = userRepository.findByUsernameAndPassword(userInfo.getUsername(), userInfo.getPassword());
            String token = generateToken(user);
            return new LoginResponse("Đăng nhập thành công", token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new LoginResponse("Đăng nhập thất bại", null);
        }
    }

    public String generateToken(userEntity userDetails) {
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

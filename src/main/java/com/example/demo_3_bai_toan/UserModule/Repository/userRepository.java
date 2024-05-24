package com.example.demo_3_bai_toan.UserModule.Repository;

import com.example.demo_3_bai_toan.UserModule.Entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface userRepository extends JpaRepository<userEntity,Long > {
    userEntity findByUsernameAndPassword(String username, String password);
}

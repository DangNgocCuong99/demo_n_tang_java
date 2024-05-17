package com.example.demo_crud.KhachHangModule.repository;

import com.example.demo_crud.KhachHangModule.Entity.customerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface customerRepository extends JpaRepository<customerEntity,Long > {
    customerEntity findByUsernameAndPassword(String username, String password);
}

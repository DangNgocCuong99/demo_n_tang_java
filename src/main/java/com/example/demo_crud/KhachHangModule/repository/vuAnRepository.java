package com.example.demo_crud.KhachHangModule.repository;

import com.example.demo_crud.KhachHangModule.Entity.vuAnEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface vuAnRepository extends JpaRepository<vuAnEntity,Long > {
//    @EntityGraph(attributePaths = "documents")
//    List<vuAnEntity> findAll();

}

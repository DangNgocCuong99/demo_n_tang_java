package com.example.demo_crud.KhachHangModule.repository;

import com.example.demo_crud.KhachHangModule.Entity.documentEntity;
import com.example.demo_crud.KhachHangModule.Interface.DocumentInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface documentRepository extends JpaRepository<documentEntity,Long > {

    List<DocumentInterface> findByParentDocumentId(Long parentDocumentId);
}

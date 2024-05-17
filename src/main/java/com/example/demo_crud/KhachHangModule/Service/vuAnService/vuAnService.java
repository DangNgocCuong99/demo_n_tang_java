package com.example.demo_crud.KhachHangModule.Service.vuAnService;

import com.example.demo_crud.KhachHangModule.Entity.documentEntity;
import com.example.demo_crud.KhachHangModule.Entity.vuAnEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface vuAnService {

    List<vuAnEntity> getVuAn();

    vuAnEntity getById(Long id) throws Exception;

    vuAnEntity create (vuAnEntity vuAn);

    documentEntity createDocument(long vuAnId, documentEntity newDocument) throws Exception;

}

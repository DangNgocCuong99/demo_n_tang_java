package com.example.demo_crud.KhachHangModule.Service.vuAnService;

import com.example.demo_crud.KhachHangModule.Entity.documentEntity;
import com.example.demo_crud.KhachHangModule.Entity.vuAnEntity;

import com.example.demo_crud.KhachHangModule.repository.documentRepository;
import com.example.demo_crud.KhachHangModule.repository.vuAnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class impVuAn implements vuAnService {
    @Autowired
    private vuAnRepository vuAnRepository;

    @Autowired
    private documentRepository documentRepository;

    @Override
    public List<vuAnEntity> getVuAn() {
        return vuAnRepository.findAll();
    }

    @Override
    public vuAnEntity getById(Long id) throws Exception {
        if (vuAnRepository.findById(id).isEmpty()){
            throw new Exception("vu an khong ton tai");
        }
        return vuAnRepository.findById(id).get();

    }

    @Override
    public vuAnEntity create(vuAnEntity vuAn) {
        return vuAnRepository.save(vuAn);
    }

    @Override
    public documentEntity createDocument(long vuAnId, documentEntity newDocument) throws Exception {
        Optional<vuAnEntity> vuAnOptional = vuAnRepository.findById(vuAnId);
        if (vuAnOptional.isPresent()) {
            newDocument.setVuAn(vuAnOptional.get());
            return documentRepository.save(newDocument);
        } else {
            throw new Exception("VuAn not found with ID: " + vuAnId);
        }
    }

}

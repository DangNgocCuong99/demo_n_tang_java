package com.example.demo_3_bai_toan.VuAnModule.Service.vuAnService;

import com.example.demo_3_bai_toan.DocumentModule.Entity.documentEntity;
import com.example.demo_3_bai_toan.DocumentModule.Interface.DocumentOfflineInterface;
import com.example.demo_3_bai_toan.DocumentModule.Repository.documentRepository;
import com.example.demo_3_bai_toan.UserModule.Entity.userEntity;
import com.example.demo_3_bai_toan.UserModule.Repository.userRepository;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnEntity;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnOffline;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnResponse;
import com.example.demo_3_bai_toan.VuAnModule.Repository.vuAnRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class impVuAn implements vuAnService {
    private final vuAnRepository vuAnRepository ;
    private final documentRepository documentRepository;
    private final userRepository userRepository;

    @Autowired
    private impVuAn(vuAnRepository vuAnRepository,documentRepository documentRepository,userRepository userRepository) {
        this.vuAnRepository = vuAnRepository;
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void intData (){
        try {
            vuAnEntity vuAnEntity = new vuAnEntity();
            vuAnEntity.setName("vu an 1");
            vuAnEntity.setDescription("vu an 1");
            vuAnEntity vuAnNew = vuAnRepository.save(vuAnEntity);
            if (vuAnNew.getId() == null) {
                throw new Exception("Tao vu an that bai");
            }
            documentEntity documentEntity = new documentEntity();
            documentEntity.setName("root_"+vuAnNew.getName());
            documentEntity.setType("folder");
            documentEntity.setVuAn(vuAnNew);
            documentEntity documentNew = documentRepository.save(documentEntity);

            if (documentNew.getId() == null) {
                throw new Exception("Tao documentNew that bai");
            }

            userEntity userEntity = new userEntity();
            userEntity.setUsername("user1");
            userEntity.setPrivate_key("user1");
            userEntity.setPassword("user1");
            userEntity userNew = userRepository.save(userEntity);

            if (userNew.getId() == null) {
                throw new Exception("Tao userNew that bai");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<vuAnEntity> getVuAn() {
        return vuAnRepository.findAll();
    }

    @Override
    public vuAnResponse getById(Long id) throws Exception {
        Optional<vuAnEntity> vuAnOptional = vuAnRepository.findById(id);
        if (vuAnOptional.isEmpty()){
            throw new Exception("vu an khong ton tai");
        }
        documentEntity documentRoot = documentRepository.findByVuAnIdAndParentDocumentIsNull(vuAnOptional.get().getId());
        return new vuAnResponse(vuAnOptional.get().getId(), vuAnOptional.get().getName(), vuAnOptional.get().getDescription(),documentRoot.getId());

    }

    @Override
    public vuAnResponse create(vuAnEntity vuAn) {
        vuAnEntity vuAnNew = vuAnRepository.save(vuAn);
        documentEntity documentEntity = new documentEntity();
        documentEntity.setName("root_"+vuAnNew.getName());
        documentEntity.setType("folder");
        documentEntity.setVuAn(vuAnNew);
        documentEntity documentNew = documentRepository.save(documentEntity);

        return new vuAnResponse(vuAnNew.getId(),vuAnNew.getName(),vuAnNew.getDescription(),documentNew.getId());
    }

    @Override
    public vuAnResponse update(Long id, vuAnResponse vuAnInfo) throws Exception {
            Optional<vuAnEntity> vuAnOptional = vuAnRepository.findById(id);
            if (vuAnOptional.isEmpty()){
                throw new Exception("khong ton tai vu an");
            }
            vuAnEntity vuAnNew = new vuAnEntity();
            vuAnNew.setId(vuAnOptional.get().getId());
            vuAnNew.setName(vuAnInfo.getName());
            vuAnNew.setDescription(vuAnInfo.getDescription());
            vuAnEntity vuAnSave = vuAnRepository.save(vuAnNew);
            documentEntity documentRoot = documentRepository.findByVuAnIdAndParentDocumentIsNull(vuAnSave.getId());
            return new vuAnResponse(vuAnSave.getId(),vuAnSave.getName(),vuAnSave.getDescription(),documentRoot.getId());
    }

    @Override
    public vuAnOffline download(Long id) throws Exception {
        Optional<vuAnEntity> vuAnOptional = vuAnRepository.findById(id);
        if (vuAnOptional.isEmpty()){
            throw new Exception("vu an khong ton tai");
        }
        List<DocumentOfflineInterface> documents = documentRepository.findByVuAnId(vuAnOptional.get().getId());

        documentEntity documentRoot = documentRepository.findByVuAnIdAndParentDocumentIsNull(vuAnOptional.get().getId());

        return new vuAnOffline(vuAnOptional.get().getId(), vuAnOptional.get().getName(), vuAnOptional.get().getDescription(),documentRoot.getId(),documents);
    }

    @Override
    public vuAnResponse delete(Long id) throws Exception {
        Optional<vuAnEntity> vuAnOptional = vuAnRepository.findById(id);
        if (vuAnOptional.isEmpty()){
            throw new Exception("vu an khong ton tai");
        }
        documentEntity document = documentRepository.findByVuAnIdAndParentDocumentIsNull(vuAnOptional.get().getId());
        documentRepository.delete(document);
        vuAnRepository.delete(vuAnOptional.get());
        return new vuAnResponse(vuAnOptional.get().getId(),vuAnOptional.get().getName(),vuAnOptional.get().getDescription(),document.getId());
    }


}

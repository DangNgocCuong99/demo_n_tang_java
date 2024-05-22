package com.example.demo_3_bai_toan.VuAnModule.Service.vuAnService;

import com.example.demo_3_bai_toan.DocumentModule.Entity.documentEntity;
import com.example.demo_3_bai_toan.DocumentModule.Repository.documentRepository;
import com.example.demo_3_bai_toan.UserModule.Entity.userEntity;
import com.example.demo_3_bai_toan.UserModule.Repository.userRepository;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnEntity;
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
        List<documentEntity> documentRoot = documentRepository.findByVuAnIdAndParentDocumentIsNull(vuAnOptional.get().getId());
        return new vuAnResponse(vuAnOptional.get().getId(), vuAnOptional.get().getName(), vuAnOptional.get().getDescription(),documentRoot.get(0).getId());

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

    

}

//package com.example.demo_3_bai_toan;
//
//import com.example.demo_3_bai_toan.DocumentModule.Entity.documentEntity;
//import com.example.demo_3_bai_toan.DocumentModule.Repository.documentRepository;
//import com.example.demo_3_bai_toan.UserModule.Entity.userEntity;
//import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnEntity;
//import com.example.demo_3_bai_toan.VuAnModule.Repository.vuAnRepository;
//import com.example.demo_3_bai_toan.UserModule.Repository.userRepository;
//import jakarta.annotation.PostConstruct;
//
//import java.util.Optional;
//
//public class InitializeDataService {
//    private final vuAnRepository vuAnRepository ;
//    private final documentRepository documentRepository;
//    private  final userRepository userRepository;
//
//    private InitializeDataService(vuAnRepository vuAnRepository,documentRepository documentRepository,userRepository userRepository) {
//        this.vuAnRepository = vuAnRepository;
//        this.documentRepository = documentRepository;
//        this.userRepository = userRepository;
//    }
//
//    public void intData (){
//        try {
//            vuAnEntity vuAnEntity = new vuAnEntity();
//            vuAnEntity.setName("vu an 1");
//            vuAnEntity.setDescription("vu an 1");
//            vuAnEntity vuAnNew = vuAnRepository.save(vuAnEntity);
//            if (vuAnNew.getId() == null) {
//                throw new Exception("Tao vu an that bai");
//            }
//            documentEntity documentEntity = new documentEntity();
//            documentEntity.setName("root_"+vuAnNew.getName());
//            documentEntity.setType("folder");
//            documentEntity.setVuAn(vuAnNew);
//            documentEntity documentNew = documentRepository.save(documentEntity);
//
//            if (documentNew.getId() == null) {
//                throw new Exception("Tao documentNew that bai");
//            }
//
//            userEntity userEntity = new userEntity();
//            userEntity.setUsername("user1");
//            userEntity.setPrivate_key("user1");
//            userEntity.setPassword("user1");
//            userEntity userNew = userRepository.save(userEntity);
//
//            if (userNew.getId() == null) {
//                throw new Exception("Tao userNew that bai");
//            }
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//}

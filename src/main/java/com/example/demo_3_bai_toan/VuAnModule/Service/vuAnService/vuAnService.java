package com.example.demo_3_bai_toan.VuAnModule.Service.vuAnService;

import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnEntity;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface vuAnService {

    List<vuAnEntity> getVuAn();

    vuAnResponse getById(Long id) throws Exception;

    vuAnEntity create (vuAnEntity vuAn);


}

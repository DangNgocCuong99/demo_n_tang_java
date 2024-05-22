package com.example.demo_3_bai_toan.VuAnModule.Service.vuAnService;

import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnEntity;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnOffline;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface vuAnService {

    List<vuAnEntity> getVuAn();

    vuAnResponse getById(Long id) throws Exception;

    vuAnResponse create (vuAnEntity vuAn);

    vuAnOffline download(Long id) throws Exception;

    vuAnResponse update(Long id,vuAnResponse vuAn) throws Exception;

    vuAnResponse delete(Long id) throws Exception;

}

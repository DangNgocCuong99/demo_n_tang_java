package com.example.demo_3_bai_toan.DocumentModule.Repository;

import com.example.demo_3_bai_toan.DocumentModule.Entity.documentEntity;
import com.example.demo_3_bai_toan.DocumentModule.Interface.DocumentInterface;
import com.example.demo_3_bai_toan.DocumentModule.Interface.DocumentOfflineInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface documentRepository extends JpaRepository<documentEntity,Long > {

    List<DocumentInterface> findByParentDocumentId(Long parentDocumentId);

    documentEntity findByVuAnIdAndParentDocumentIsNull(@Param("vuAnId") Long vuAnId);

    List<documentEntity> findByType(String type);

    List<DocumentOfflineInterface> findByVuAnId(@Param("vuAnId") Long vuAnId);

    List<DocumentInterface> findByVuAnIdAndType(@Param("vuAnId") Long vuAnId ,@Param("type") String type );


}

package com.example.demo_3_bai_toan.DocumentModule.Repository;

import com.example.demo_3_bai_toan.DocumentModule.Entity.documentEntity;
import com.example.demo_3_bai_toan.DocumentModule.Interface.DocumentInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface documentRepository extends JpaRepository<documentEntity,Long > {

    List<DocumentInterface> findByParentDocumentId(Long parentDocumentId);

    @Query("SELECT d FROM documentEntity d WHERE d.vuAn.id = :vuAnId AND d.parentDocument IS NULL")
    List<documentEntity> findByVuAnIdAndParentDocumentIsNull(@Param("vuAnId") Long vuAnId);
}

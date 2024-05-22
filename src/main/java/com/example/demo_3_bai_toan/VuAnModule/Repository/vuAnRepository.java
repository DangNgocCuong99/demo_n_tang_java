package com.example.demo_3_bai_toan.VuAnModule.Repository;

import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface vuAnRepository extends JpaRepository<vuAnEntity,Long > {
//    @EntityGraph(attributePaths = "documents")
//    List<vuAnEntity> findAll();

}

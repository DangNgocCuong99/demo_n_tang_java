package com.example.demo_3_bai_toan.DocumentModule.Interface;

import java.util.Optional;



public interface DocumentOfflineInterface
{
    Long getId();;
    String getName();
    String getType();
    String getUrl();
    Optional<Long> getParentDocumentId();
}

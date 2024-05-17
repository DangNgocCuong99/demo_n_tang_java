package com.example.demo_crud.KhachHangModule.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@Table(name = "document")
@AllArgsConstructor
@NoArgsConstructor
public class documentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String type;

    @ManyToOne
    @JoinColumn(name = "parent_document_id")
    private documentEntity parentDocument;

//    @OneToMany(mappedBy = "parentDocument")
//    @JsonIgnoreProperties("parentDocument")
//    private List<documentEntity> childDocuments;

    @ManyToOne
    @JoinColumn(name="vu_an_id")
    @JsonIgnoreProperties("documents")
    private vuAnEntity vuAn;



}

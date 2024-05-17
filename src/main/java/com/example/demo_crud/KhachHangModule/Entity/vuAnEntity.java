package com.example.demo_crud.KhachHangModule.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "vu_an")
@AllArgsConstructor
@NoArgsConstructor
public class vuAnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

//    @Getter
//    @OneToMany(mappedBy = "vuAn", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<documentEntity> documents = new ArrayList<>();;

}
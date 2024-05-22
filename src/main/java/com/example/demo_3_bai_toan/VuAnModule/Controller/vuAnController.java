package com.example.demo_3_bai_toan.VuAnModule.Controller;

import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnEntity;
import com.example.demo_3_bai_toan.VuAnModule.Entity.vuAnResponse;
import com.example.demo_3_bai_toan.VuAnModule.Service.vuAnService.vuAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Vu An", description = "Vu An management APIs")
@RestController
@RequestMapping("/vu-an")
public class vuAnController {
    @Autowired
    private vuAnService vuAnService;

    @GetMapping("/get")
    public ResponseEntity<List<vuAnEntity>> getAll(){
        return new ResponseEntity<>(vuAnService.getVuAn(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<vuAnResponse> getById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(vuAnService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody vuAnEntity vuAn) {
        return new ResponseEntity<>(vuAnService.create(vuAn), HttpStatus.OK);
    }
    
}


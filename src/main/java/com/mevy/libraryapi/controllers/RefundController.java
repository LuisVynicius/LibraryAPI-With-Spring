package com.mevy.libraryapi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mevy.libraryapi.entities.Refund;
import com.mevy.libraryapi.entities.dto.RefundCreateDTO;
import com.mevy.libraryapi.entities.dto.RefundUpdateDTO;
import com.mevy.libraryapi.services.RefundService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/refund")
public class RefundController {
    
    @Autowired
    private RefundService refundService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Refund> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(refundService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid RefundCreateDTO refundDTO){
        Refund refund = refundService.fromDTO(refundDTO);
        refund = refundService.create(refund);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(refund.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid RefundUpdateDTO refundDTO){
        Refund refund = refundService.fromDTO(refundDTO);
        refundService.update(refund);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        refundService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

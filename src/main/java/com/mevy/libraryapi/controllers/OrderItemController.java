package com.mevy.libraryapi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mevy.libraryapi.entities.OrderItem;
import com.mevy.libraryapi.entities.pk.OrderItemPK;
import com.mevy.libraryapi.services.OrderItemService;

@RestController
@RequestMapping("/orderitem")
public class OrderItemController {
    
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderItem> findById(@PathVariable OrderItemPK id){
        return ResponseEntity.ok().body(orderItemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderItem orderItem){
        orderItem = orderItemService.create(orderItem);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(orderItem.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable OrderItemPK id){
        orderItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}


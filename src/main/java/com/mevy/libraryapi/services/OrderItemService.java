package com.mevy.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.OrderItem;
import com.mevy.libraryapi.entities.pk.OrderItemPK;
import com.mevy.libraryapi.repositories.OrderItemRepository;
import com.mevy.libraryapi.services.exceptions.DataBindingViolationException;
import com.mevy.libraryapi.services.exceptions.ResourceNotFoundException;

@Service
public class OrderItemService {
    
    @Autowired
    private OrderItemRepository orderRepository;

    public OrderItem findById(OrderItemPK id){
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(OrderItem.class, id));
    }

    @Transactional
    public OrderItem create(OrderItem orderItem){
        orderItem = orderRepository.save(orderItem);
        return orderItem;
    }

    public void deleteById(OrderItemPK id){
        findById(id);
        try {
            orderRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new DataBindingViolationException();
        }
    }
    
}

package com.mevy.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.Order;
import com.mevy.libraryapi.entities.dto.OrderCreateDTO;
import com.mevy.libraryapi.repositories.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    public Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found. id: " + id));
    }

    @Transactional
    public Order create(Order order){
        order.setId(null);
        order = orderRepository.save(order);
        return order;
    }

    public void deleteById(Long id){
        try {
            orderRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new RuntimeException("Data integrity exception. ");
        }
    }

    public Order fromDTO(OrderCreateDTO orderDTO){
        Order order = new Order();
        order.setMoment(orderDTO.getMoment());
        order.setUser(orderDTO.getUser());
        return order;
    }

}

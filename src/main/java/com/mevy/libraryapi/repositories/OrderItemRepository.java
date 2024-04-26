package com.mevy.libraryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.libraryapi.entities.OrderItem;
import com.mevy.libraryapi.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}

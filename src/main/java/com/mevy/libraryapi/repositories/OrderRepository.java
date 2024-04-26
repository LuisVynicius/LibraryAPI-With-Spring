package com.mevy.libraryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.libraryapi.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}

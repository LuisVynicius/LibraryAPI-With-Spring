package com.mevy.libraryapi.entities;

import java.io.Serializable;

import com.mevy.libraryapi.entities.pk.OrderItemPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_order_item")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class OrderItem implements Serializable{
    
    @EmbeddedId
    @Column(unique = true, nullable = false)
    private OrderItemPK id = new OrderItemPK();
    
    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Integer quantity;

    public OrderItem(Order order, Book book, Float price, Integer quantity){
        this.id.setOrder(order);
        this.id.setBook(book);
        this.price = price;
        this.quantity = quantity;
    }

    public Order getOrder(){
        return id.getOrder();
    }

    public void setOrder(Order order){
        id.setOrder(order);
    }

    public Book getBook(){
        return id.getBook();
    }

    public void setBook(Book book){
        id.setBook(book);
    }
}

package com.mevy.libraryapi.entities.pk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mevy.libraryapi.entities.Book;
import com.mevy.libraryapi.entities.Order;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class OrderItemPK {
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}

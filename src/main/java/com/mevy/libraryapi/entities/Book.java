package com.mevy.libraryapi.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_book")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name", "author"})
public class Book implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String path;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float price;

    @ManyToMany
    @JoinTable(
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "id.book")
    private Set<OrderItem> items = new HashSet<>();

    public Book(Long id, String name, String path, String author, Integer quantity, Float price) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }

}

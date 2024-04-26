package com.mevy.libraryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.libraryapi.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    
}

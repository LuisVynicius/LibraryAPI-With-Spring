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

import com.mevy.libraryapi.entities.Book;
import com.mevy.libraryapi.entities.dto.BookCreateDTO;
import com.mevy.libraryapi.entities.dto.BookUpdateDTO;
import com.mevy.libraryapi.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {
    
     @Autowired
    private BookService bookService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid BookCreateDTO bookCreateDTO){
        Book book = bookService.fromDTO(bookCreateDTO);
        book = bookService.create(book);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/id")
                        .buildAndExpand(book.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid BookUpdateDTO bookUpdateDTO){
        Book book = bookService.fromDTO(bookUpdateDTO);
        bookService.update(book);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}

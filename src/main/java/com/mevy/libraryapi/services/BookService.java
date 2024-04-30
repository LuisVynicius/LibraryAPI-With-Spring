package com.mevy.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.Book;
import com.mevy.libraryapi.entities.dto.BookCreateDTO;
import com.mevy.libraryapi.entities.dto.BookUpdateDTO;
import com.mevy.libraryapi.repositories.BookRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    public Book findById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found. id: " + id));
    }

    @Transactional
    public Book create(Book book){
        book.setId(null);
        book = bookRepository.save(book);
        return book;
    }

    public void deleteById(Long id){
        try {
            bookRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new RuntimeException("Data integrity exception. ");
        }
    }

    @Transactional
    public void update(Book book){
        try {
            Book oldBook = bookRepository.getReferenceById(book.getId());
            updateData(oldBook, book);
        } catch(EntityNotFoundException e){
            new RuntimeException("Book not found. id: " + book.getId());
        }
    }

    public void updateData(Book oldBook, Book book){
        oldBook.setPath(book.getPath());
        oldBook.setPrice(book.getPrice());
    }

    public Book fromDTO(BookCreateDTO bookDTO){
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setPath(bookDTO.getPath());
        book.setAuthor(bookDTO.getAuthor());
        book.setQuantity(bookDTO.getQuantity());
        book.setPrice(bookDTO.getPrice());
        return book;
    }

    public Book fromDTO(BookUpdateDTO bookDTO){
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setPath(book.getPath());
        book.setPrice(bookDTO.getPrice());
        return book;
    }

}

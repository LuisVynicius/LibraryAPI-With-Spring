package com.mevy.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.User;
import com.mevy.libraryapi.entities.dto.UserCreateDTO;
import com.mevy.libraryapi.entities.dto.UserUpdateDTO;
import com.mevy.libraryapi.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found. id: " + id));
    }

    @Transactional
    public void create(User user){
        user.setId(null);
        userRepository.save(user);
    }

    public void deleteById(Long id){
        try {
            userRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new RuntimeException("Data integrity exception. ");
        }
    }

    @Transactional
    public void update(User user){
        try {
            User oldUser = userRepository.getReferenceById(user.getId());
            updateData(oldUser, user);
        } catch(EntityNotFoundException e){
            new RuntimeException("User not found. id: " + user.getId());
        }
    }

    public void updateData(User oldUser, User user){
        oldUser.setPassword(user.getPassword());
    }

    public User fromDTO(@Valid UserCreateDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public User fromDTO(@Valid UserUpdateDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setPassword(userDTO.getPassword());
        return user;
    }

}

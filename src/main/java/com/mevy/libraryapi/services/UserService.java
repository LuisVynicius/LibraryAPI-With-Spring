package com.mevy.libraryapi.services;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.User;
import com.mevy.libraryapi.entities.dto.UserCreateDTO;
import com.mevy.libraryapi.entities.dto.UserUpdateDTO;
import com.mevy.libraryapi.entities.enums.ProfileEnum;
import com.mevy.libraryapi.repositories.UserRepository;
import com.mevy.libraryapi.services.exceptions.DataBindingViolationException;
import com.mevy.libraryapi.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(User.class, id));
    }

    @Transactional
    public User create(User user){
        user.setId(null);
        user.setProfiles(Stream.of(ProfileEnum.ROLE_USER.getCode()).collect(Collectors.toSet()));
        user = userRepository.save(user);
        return user;
    }

    public void deleteById(Long id){
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new DataBindingViolationException();
        }
    }

    @Transactional
    public void update(User user){
        try {
            User oldUser = userRepository.getReferenceById(user.getId());
            updateData(oldUser, user);
        } catch(EntityNotFoundException e){
            new ResourceNotFoundException(User.class, user.getId());
        }
    }

    private void updateData(User oldUser, User user){
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

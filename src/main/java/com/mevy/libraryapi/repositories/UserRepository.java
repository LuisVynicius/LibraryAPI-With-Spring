package com.mevy.libraryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

    @Transactional(readOnly = true)
    User findByUsername(String username);

}

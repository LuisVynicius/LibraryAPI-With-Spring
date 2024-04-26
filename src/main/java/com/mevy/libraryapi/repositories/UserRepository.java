package com.mevy.libraryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.libraryapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}

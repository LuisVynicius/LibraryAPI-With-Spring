package com.mevy.libraryapi.entities.dto;

import java.time.Instant;

import com.mevy.libraryapi.entities.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderCreateDTO {
    
    @NotNull(message = "This field must not be null. ")
    private Instant moment;

    @NotNull(message = "This field must not be null. ")
    private User user;

}

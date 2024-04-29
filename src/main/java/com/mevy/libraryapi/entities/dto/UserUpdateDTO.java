package com.mevy.libraryapi.entities.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {
    
    @NotNull(message = "This field must not be null. ")
    @Size(min = 5, max = 25, message = "This field must have between 5 and 25 characters. ")
    private String password;

}

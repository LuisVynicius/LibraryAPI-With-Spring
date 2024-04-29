package com.mevy.libraryapi.entities.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateDTO {
    
    @NotNull(message = "This field must not be null. ")
    @Size(min = 3, max = 15, message = "This field must have between 3 and 15 characters. ")
    private String username;

    @NotNull(message = "This field must not be null. ")
    @Size(min = 3, max = 15, message = "This field must have between 15 and 30 characters. ")
    private String email;

    @NotNull(message = "This field must not be null. ")
    @Size(min = 5, max = 25, message = "This field must have between 5 and 25 characters. ")
    private String password;

}

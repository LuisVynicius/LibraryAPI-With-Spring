package com.mevy.libraryapi.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookUpdateDTO {
    
    private Long id;

    @NotBlank(message = "This field must have at least one character. ")
    private String path;

    @Positive(message = "This field must not be negative. ")
    private Float price;


}

package com.mevy.libraryapi.entities.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookCreateDTO {
    
    @NotBlank(message = "This field must have at least one character. ")
    private String name;

    @NotBlank(message = "This field must have at least one character. ")
    private String path;

    @NotBlank(message = "This field must have at least one character. ")
    private String author;

    @Min(value = 0, message = "This field must not be negative. ")
    private Integer quantity;

    @Positive(message = "This field must not be negative. ")
    private Float price;

}

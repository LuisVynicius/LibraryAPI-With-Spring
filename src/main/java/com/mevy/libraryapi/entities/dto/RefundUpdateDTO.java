package com.mevy.libraryapi.entities.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefundUpdateDTO {
    
    @NotNull(message = "This field must not be null. ")
    private Long id;

    @NotBlank(message = "This field must have at least one character. ")
    private String reason;

}

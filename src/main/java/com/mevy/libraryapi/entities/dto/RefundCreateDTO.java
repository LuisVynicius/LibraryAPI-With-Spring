package com.mevy.libraryapi.entities.dto;

import java.time.Instant;

import com.mevy.libraryapi.entities.Order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefundCreateDTO {
    
    @NotNull(message = "This field must not be null. ")
    private Instant moment;

    @NotBlank(message = "This field must have at least one character. ")
    private String reason;

    @NotNull(message = "This field must not be null. ")
    private Order order;

}

package com.mahesh.gamingclubmanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number must contain exactly 10 digits"
    )
    private String mobileNumber;
}
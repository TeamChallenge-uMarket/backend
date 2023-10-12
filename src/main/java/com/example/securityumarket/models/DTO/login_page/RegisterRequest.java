package com.example.securityumarket.models.DTO.login_page;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends PasswordRequest {

    @NotBlank(message = "username is required")
    @Size(min = 2, max = 50, message
            = "user name must be between 2 and 50 characters")
    private String name;

    @Pattern(regexp = "((\\+?38)?\\(?\\d{3}\\)?[\\s.-]?(\\d{7}|\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}|\\d{3}-\\d{4}))|^$", message = "Invalid phone format")
    private String phone;

    private AddressDTO addressDTO;
}

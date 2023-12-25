package com.example.securityumarket.dto.entities.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailsDTO extends UserBaseDTO {
    @Pattern(regexp = "((\\+?38)?\\(?\\d{3}\\)?[\\s.-]?(\\d{7}|\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}|\\d{3}-\\d{4}))|^$", message = "Invalid phone format")
    private String phone;
    private Long cityId;    
    @Size(max = 255)
    private String photoUrl;
}

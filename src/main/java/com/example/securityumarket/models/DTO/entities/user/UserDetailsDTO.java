package com.example.securityumarket.models.DTO.entities.user;

import com.example.securityumarket.models.entities.City;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailsDTO extends UserBaseDTO {
    @Pattern(regexp = "((\\+?38)?\\(?\\d{3}\\)?[\\s.-]?(\\d{7}|\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}|\\d{3}-\\d{4}))|^$", message = "Invalid phone format")
    private String phone;
    private Long cityId;    
    @Size(max = 255)
    private String photoUrl;
}

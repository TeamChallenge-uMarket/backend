package com.example.securityumarket.dto.entities.user;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransportPageUserDetailsDto extends UserDetailsDTO {
    private LocalDateTime joinDate;
}

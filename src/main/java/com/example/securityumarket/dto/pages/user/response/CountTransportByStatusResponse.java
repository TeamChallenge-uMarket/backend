package com.example.securityumarket.dto.pages.user.response;

import com.example.securityumarket.models.Transport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountTransportByStatusResponse {

    Transport.Status status;

    Long count;

}

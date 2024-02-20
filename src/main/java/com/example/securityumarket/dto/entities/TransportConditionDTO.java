package com.example.securityumarket.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransportConditionDTO implements Serializable {

    private long transportConditionId;

    private String transportCondition;
}

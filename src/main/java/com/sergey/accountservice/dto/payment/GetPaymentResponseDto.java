package com.sergey.accountservice.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentResponseDto {
    private String name;
    private String lastname;
    private String period;
    private String salary;
}

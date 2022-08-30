package com.sergey.accountservice.dto.payment;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentResponseDtos {
    @JsonUnwrapped
    List<GetPaymentResponseDto> paymentResponseDtos;
}

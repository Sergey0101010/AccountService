package com.sergey.accountservice.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntityDto {

    @NotBlank
    private String employee;

    @NotBlank
    private String period;

    @NotBlank
    private Long salary;


}

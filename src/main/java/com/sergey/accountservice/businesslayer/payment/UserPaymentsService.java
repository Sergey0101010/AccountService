package com.sergey.accountservice.businesslayer.payment;

import com.sergey.accountservice.dto.payment.GetPaymentResponseDto;
import com.sergey.accountservice.dto.payment.OperationStatusResponseDto;
import com.sergey.accountservice.dto.payment.PaymentEntityDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface UserPaymentsService {
     List<GetPaymentResponseDto> getPayment(UserDetails userDetails, Map<String, String> params);

     OperationStatusResponseDto addEmployeePayments(List<PaymentEntityDto> paymentEntityDtoList);

     OperationStatusResponseDto updateEmployeePayment(PaymentEntityDto paymentEntityDto);
}

package com.sergey.accountservice.presentationlayer;

import com.sergey.accountservice.businesslayer.payment.UserPaymentsService;
import com.sergey.accountservice.dto.payment.GetPaymentResponseDto;
import com.sergey.accountservice.dto.payment.OperationStatusResponseDto;
import com.sergey.accountservice.dto.payment.PaymentEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    final UserPaymentsService userPaymentsService;



    @GetMapping("/empl/payment")
    public List<GetPaymentResponseDto> getPayment(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestParam Map<String, String> params)  {

        return userPaymentsService.getPayment(userDetails, params);
    }


    @PostMapping("/acct/payments")
    public OperationStatusResponseDto addEmployeePayments(
            @RequestBody List<PaymentEntityDto> paymentEntityDtoList) {
        return userPaymentsService.addEmployeePayments(paymentEntityDtoList);
    }


    @PutMapping("/acct/payments")
    public OperationStatusResponseDto updateEmployeePayments(
            @RequestBody PaymentEntityDto paymentEntityDto
    ) {
        return userPaymentsService.updateEmployeePayment(paymentEntityDto);
    }
}

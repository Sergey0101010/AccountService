package com.sergey.accountservice.dto.signin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChangedPasswordResponseDto {
    private String email;
    private String status;

}

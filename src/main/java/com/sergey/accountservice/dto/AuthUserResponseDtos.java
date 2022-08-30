package com.sergey.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.sergey.accountservice.dto.signin.AuthUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserResponseDtos {

    @JsonUnwrapped
    private List<AuthUserResponseDto> dtos;
}

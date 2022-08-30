package com.sergey.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRoleRequestDto {

    @NotBlank
    private String user;

    private String role;

    private String operation;

}

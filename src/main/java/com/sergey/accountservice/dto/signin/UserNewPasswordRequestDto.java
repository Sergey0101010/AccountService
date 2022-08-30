package com.sergey.accountservice.dto.signin;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserNewPasswordRequestDto {


    @NotBlank
    @Size(min = 12)
    private String newPassword;

    @JsonGetter(value = "new_password")
    public String getNewPassword() {
        return newPassword;
    }
}

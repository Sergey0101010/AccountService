package com.sergey.accountservice.dto.signin;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AuthUserRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    @Pattern(regexp = "\\w+@acme.com")
    private String email;

    @NotBlank
    @Size(min = 12)
    private String password;

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
}

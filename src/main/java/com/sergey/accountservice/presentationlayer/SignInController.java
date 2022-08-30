package com.sergey.accountservice.presentationlayer;

import com.sergey.accountservice.businesslayer.register.UserRegisterDataService;
import com.sergey.accountservice.dto.signin.AuthUserRequestDto;
import com.sergey.accountservice.dto.signin.AuthUserResponseDto;
import com.sergey.accountservice.dto.signin.UserChangedPasswordResponseDto;
import com.sergey.accountservice.dto.signin.UserNewPasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class SignInController {

    final UserRegisterDataService userRegisterDataService;

    @PostMapping("/auth/signup")
    public AuthUserResponseDto userSignUp(@Valid @RequestBody AuthUserRequestDto userData) {
        return userRegisterDataService.signUpUser(userData);
    }


    @PostMapping("/auth/changepass")
    public UserChangedPasswordResponseDto changePassword(
            @Valid @RequestBody UserNewPasswordRequestDto userNewPasswordRequestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return userRegisterDataService.changePassword(userNewPasswordRequestDto, userDetails);
    }
}

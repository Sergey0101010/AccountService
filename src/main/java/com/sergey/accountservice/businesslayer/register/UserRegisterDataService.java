package com.sergey.accountservice.businesslayer.register;

import com.sergey.accountservice.dto.signin.AuthUserRequestDto;
import com.sergey.accountservice.dto.signin.AuthUserResponseDto;
import com.sergey.accountservice.dto.signin.UserChangedPasswordResponseDto;
import com.sergey.accountservice.dto.signin.UserNewPasswordRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRegisterDataService {
    AuthUserResponseDto signUpUser(AuthUserRequestDto userData);


    UserChangedPasswordResponseDto changePassword(UserNewPasswordRequestDto userNewPasswordRequestDto,
                                                  UserDetails userDetails);
}

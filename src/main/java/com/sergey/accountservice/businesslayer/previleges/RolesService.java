package com.sergey.accountservice.businesslayer.previleges;

import com.sergey.accountservice.dto.DeleteUserResponseDto;
import com.sergey.accountservice.dto.UpdateUserRoleRequestDto;
import com.sergey.accountservice.dto.signin.AuthUserResponseDto;

import java.util.List;

public interface RolesService {
    List<AuthUserResponseDto> getAllUsersFromDb();

    DeleteUserResponseDto deleteUser(String userEmail);

    AuthUserResponseDto updateUser(UpdateUserRoleRequestDto updateRoleRequestDto);
}

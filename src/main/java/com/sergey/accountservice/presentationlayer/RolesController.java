package com.sergey.accountservice.presentationlayer;

import com.sergey.accountservice.businesslayer.previleges.RolesService;
import com.sergey.accountservice.dto.DeleteUserResponseDto;
import com.sergey.accountservice.dto.UpdateUserRoleRequestDto;
import com.sergey.accountservice.dto.signin.AuthUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
public class RolesController {

    private final RolesService rolesService;


    @GetMapping("/admin/user")
    public List<AuthUserResponseDto> getAllUsers() {
        return rolesService.getAllUsersFromDb();
    }

    @DeleteMapping(value = {"/admin/user/{userEmail}", "/admin/user/", "/admin/user"})
    public DeleteUserResponseDto deleteUser(@PathVariable String userEmail) {
        return rolesService.deleteUser(userEmail);
    }

    @PutMapping("/admin/user/role")
    public AuthUserResponseDto updateUser(@RequestBody @Valid UpdateUserRoleRequestDto updateRoleRequestDto) {
        return rolesService.updateUser(updateRoleRequestDto);
    }


}

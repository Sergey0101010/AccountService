package com.sergey.accountservice.dto.signin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sergey.accountservice.databaselayer.GroupEntity;
import com.sergey.accountservice.databaselayer.UserEntity;
import com.sergey.accountservice.presentationlayer.RolesSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonSerialize(using = RolesSerializer.class)
public class AuthUserResponseDto {
    private Long id;
    private String name;
    private String lastname;
    private String email;


    private List<String> roles;


    public AuthUserResponseDto(UserEntity userEntity) {
        id = userEntity.getId();
        name = userEntity.getName();
        lastname = userEntity.getLastname();
        email = userEntity.getEmail();
        //may be mistake
        roles = userEntity.getUserGroups().stream()
                .map(GroupEntity::getRole)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }
}

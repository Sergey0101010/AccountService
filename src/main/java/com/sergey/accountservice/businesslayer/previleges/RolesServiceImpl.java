package com.sergey.accountservice.businesslayer.previleges;

import com.sergey.accountservice.databaselayer.GroupEntity;
import com.sergey.accountservice.databaselayer.UserEntity;
import com.sergey.accountservice.dto.DeleteUserResponseDto;
import com.sergey.accountservice.dto.UpdateUserRoleRequestDto;
import com.sergey.accountservice.dto.signin.AuthUserResponseDto;
import com.sergey.accountservice.exception.DeleteAdminException;
import com.sergey.accountservice.exception.DeleteUserException;
import com.sergey.accountservice.exception.UpdateUserRoleException;
import com.sergey.accountservice.persistencelayer.GroupRepository;
import com.sergey.accountservice.persistencelayer.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RolesServiceImpl implements RolesService {

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;


    @Override
    public List<AuthUserResponseDto> getAllUsersFromDb() {
        Iterable<UserEntity> userRepositoryAll = userRepository.findAll();
        List<AuthUserResponseDto> returnedDtosList = new ArrayList<>();
        for (UserEntity userEntity : userRepositoryAll) {
            AuthUserResponseDto responseDto = getAuthUserResponseDtoFromEntity(userEntity);
            returnedDtosList.add(responseDto);
        }
        return returnedDtosList;
    }

    @Override
    public DeleteUserResponseDto deleteUser(String userEmail) {
        if (!userRepository.existsByEmailIgnoreCase(userEmail)) {
            throw new DeleteUserException("User not found!");
        } else if (getListOfUserRoles(userEmail).contains("ROLE_ADMINISTRATOR")) {

            throw new DeleteAdminException("Can't remove ADMINISTRATOR role!");

        } else {
            UserEntity userEntityDelete = userRepository.findUserEntitiesByEmailIgnoreCase(userEmail);
            userRepository.delete(userEntityDelete);
            log.info("User " + userEntityDelete.getEmail() + " was deleted");
            return new DeleteUserResponseDto(userEmail, "Deleted successfully!");
        }
    }

    private List<String> getListOfUserRoles(String userEmail) {
        return userRepository
                .findUserEntitiesByEmailIgnoreCase(userEmail)
                .getUserGroups()
                .stream().map(GroupEntity::getRole)
                .collect(Collectors.toList());
    }


    @Override
    public AuthUserResponseDto updateUser(UpdateUserRoleRequestDto updateRoleRequestDto) {


        UserEntity userEntityNotNull = Optional
                .ofNullable(userRepository.findUserEntitiesByEmailIgnoreCase(updateRoleRequestDto.getUser()))
                .orElseThrow(() -> new DeleteUserException("User not found!"));


        GroupEntity groupEntityNotNull = Optional
                .ofNullable(groupRepository.findByRole("ROLE_" + updateRoleRequestDto.getRole()))
                .orElseThrow(() -> new DeleteUserException("Role not found!"));

        if (updateRoleRequestDto.getOperation().equals("REMOVE")) {


            if (!userEntityNotNull.getUserGroups().contains(groupEntityNotNull)) {

                throw new UpdateUserRoleException("The user does not have a role!");
            } else if (getListOfUserRoles(userEntityNotNull.getEmail()).contains("ROLE_ADMINISTRATOR")) {
                throw new UpdateUserRoleException("Can't remove ADMINISTRATOR role!");
            } else if (userEntityNotNull.getUserGroups().size() == 1) {
                throw new UpdateUserRoleException("The user must have at least one role!");
            } else {
                List<GroupEntity> groups = new ArrayList<>(userEntityNotNull.getUserGroups());

                boolean remove = groups.remove(groupEntityNotNull);
                log.info("Role " + updateRoleRequestDto.getOperation() +  " was removed from user: "
                        + userEntityNotNull.getEmail());
                userEntityNotNull.setUserGroups(groups);

            }
        } else {

            if (!(userEntityNotNull.getUserGroups()
                    .stream().map(GroupEntity::getRoleGroup).toList()
                    .contains(groupEntityNotNull.getRoleGroup()))) {
                throw new UpdateUserRoleException("The user cannot combine administrative and business roles!");
            } else if (userEntityNotNull.getUserGroups()
                    .stream().map(GroupEntity::getRole)
                    .toList().contains(groupEntityNotNull.getRole())) {

            } else {
                List<GroupEntity> groups = new ArrayList<>(userEntityNotNull.getUserGroups());

                groups.add(groupEntityNotNull);
                log.info("New role granted to user: " + userEntityNotNull.getEmail());
                userEntityNotNull.setUserGroups(groups);

            }
        }
        userRepository.save(userEntityNotNull);

        return getAuthUserResponseDtoFromEntity(userEntityNotNull);

    }

    private AuthUserResponseDto getAuthUserResponseDtoFromEntity(UserEntity userEntity) {
        AuthUserResponseDto responseDto = new AuthUserResponseDto();
        responseDto.setEmail(userEntity.getEmail());
        responseDto.setId(userEntity.getId());
        responseDto.setName(userEntity.getName());
        responseDto.setLastname(userEntity.getLastname());
        responseDto.setRoles(userEntity.getUserGroups().stream()
                .map(GroupEntity::getRole)
                        .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList()));
        return responseDto;
    }

}

package com.sergey.accountservice.businesslayer.register;

import com.sergey.accountservice.databaselayer.GroupEntity;
import com.sergey.accountservice.databaselayer.UserEntity;
import com.sergey.accountservice.dto.signin.AuthUserRequestDto;
import com.sergey.accountservice.dto.signin.AuthUserResponseDto;
import com.sergey.accountservice.dto.signin.UserChangedPasswordResponseDto;
import com.sergey.accountservice.dto.signin.UserNewPasswordRequestDto;
import com.sergey.accountservice.exception.ProvidedPasswordException;
import com.sergey.accountservice.exception.UserRegistrationException;
import com.sergey.accountservice.persistencelayer.GroupRepository;
import com.sergey.accountservice.persistencelayer.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegisterDataServiceImpl implements UserRegisterDataService {


    final UserRepository userRepository;
    final PasswordEncoder encoder;

    final GroupRepository groupRepository;

    //represents set of breached passwords, which user can't use
    final Set<String> breachedPassRepository = Set.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    // user registration
    public AuthUserResponseDto signUpUser(AuthUserRequestDto userData) {

        if (userRepository.existsByEmailIgnoreCase(userData.getEmail())) { //check if user with this email exists
            throw new UserRegistrationException("User exist!");
        } else if (breachedPassRepository
                .contains(userData.getPassword())) {
            throw new ProvidedPasswordException("The password is in the hacker's database!");
        } else {
            UserEntity newRegisterUser =
                    getUserEntityFromAuthRequestDto(userData); // get UserEntity from AuthUserRequestDto
            UserEntity save = userRepository.save(newRegisterUser);
            log.info("New user " + save.getEmail() + " was created");

            return getAuthUserResponseDtoFromRequest(save);
        }

    }


    @Override
    public UserChangedPasswordResponseDto changePassword(
            UserNewPasswordRequestDto userNewPasswordRequestDto, UserDetails userDetails) {
        UserDetails details = Optional.ofNullable(userDetails)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        UserEntity userEntity = userRepository.findUserEntitiesByEmailIgnoreCase(details.getUsername());
        String hashedUserPassword = userEntity.getPassword();
        if (breachedPassRepository
                .contains(userNewPasswordRequestDto.getNewPassword())) {
            throw new ProvidedPasswordException("The password is in the hacker's database!");
        } else if (encoder.matches(userNewPasswordRequestDto.getNewPassword(), hashedUserPassword)) {
            throw new ProvidedPasswordException("The passwords must be different!");
        } else {
            userEntity.setPassword(encoder.encode(userNewPasswordRequestDto.getNewPassword()));
            log.info("User " + userEntity.getEmail() + " changed password");
            userRepository.save(userEntity);
            return new UserChangedPasswordResponseDto(userDetails.getUsername(),
                    "The password has been updated successfully");
        }
    }

    private AuthUserResponseDto getAuthUserResponseDtoFromRequest(UserEntity userData) {
        AuthUserResponseDto returnedUserData = new AuthUserResponseDto();
        returnedUserData.setName(userData.getName());
        returnedUserData.setId(userData.getId());
        returnedUserData.setEmail(userData.getEmail());
        returnedUserData.setLastname(userData.getLastname());
        returnedUserData.setRoles(userData.getUserGroups().
                stream()
                .map(GroupEntity::getRole)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList()));
        return returnedUserData;
    }

    private UserEntity getUserEntityFromAuthRequestDto(AuthUserRequestDto userData) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(userData.getEmail());
        newUser.setPassword(encoder.encode(userData.getPassword()));
        newUser.setName(userData.getName());
        newUser.setLastname(userData.getLastname());

        if (userRepository.count() == 0) {
            newUser.getUserGroups().add(groupRepository.findByRole("ROLE_ADMINISTRATOR"));

        } else {
            newUser.getUserGroups().add(groupRepository.findByRole("ROLE_USER"));
        }

        return newUser;
    }
}

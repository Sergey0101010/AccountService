package com.sergey.accountservice.persistencelayer;

import com.sergey.accountservice.databaselayer.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUserEntitiesByEmailIgnoreCase(@NotBlank String email);

    boolean existsByEmailIgnoreCase(String email);
}

package com.sergey.accountservice.persistencelayer;

import com.sergey.accountservice.databaselayer.GroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<GroupEntity, Long> {
    GroupEntity findByRole(String role);
}

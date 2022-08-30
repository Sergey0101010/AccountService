package com.sergey.accountservice.businesslayer.previleges;

import com.sergey.accountservice.databaselayer.GroupEntity;
import com.sergey.accountservice.persistencelayer.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Loads list of roles, which user might have at start of application
 * Should be deleted after first start of application
 */
@Component
public class DataLoader {

    private final GroupRepository groupRepository;

    @Autowired
    public DataLoader(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        createRoles();
    }

    private void createRoles() {

        try {
            groupRepository.save(new GroupEntity("ROLE_ADMINISTRATOR", "administrative"));
            groupRepository.save(new GroupEntity("ROLE_USER", "business"));
            groupRepository.save(new GroupEntity("ROLE_ACCOUNTANT", "business"));
        } catch (Exception e) {

        }
    }
}

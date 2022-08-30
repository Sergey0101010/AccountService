package com.sergey.accountservice.databaselayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", unique = true, nullable = false)
    private String role;

    @Column(name = "role_group")
    private String roleGroup;

    @ManyToMany(mappedBy = "userGroups")
    List<UserEntity> users;


    public GroupEntity(String role, String roleGroup) {
        this.role = role;
        this.roleGroup = roleGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupEntity that = (GroupEntity) o;

        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        return users != null ? users.equals(that.users) : that.users == null;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}

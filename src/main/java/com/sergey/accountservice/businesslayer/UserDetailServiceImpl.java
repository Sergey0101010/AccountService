package com.sergey.accountservice.businesslayer;

import com.sergey.accountservice.databaselayer.GroupEntity;
import com.sergey.accountservice.databaselayer.UserEntity;
import com.sergey.accountservice.persistencelayer.UserRepository;
import com.sergey.accountservice.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntitiesByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + email);
        }

        UserDetailsImpl newUserDetailsImpl = new UserDetailsImpl.UserDetailsImplBuilder()
                .email(user.getEmail())
                .password(user.getPassword())
                .rolesAndAuthorities(getAuthorites(user))
                .build();

        return newUserDetailsImpl;

    }

    private List<GrantedAuthority> getAuthorites(UserEntity userEntity) {

        List<GroupEntity> userGroups = userEntity.getUserGroups();
        List<GrantedAuthority> authorities = new ArrayList<>(userGroups.size());
        for (GroupEntity userGroup : userGroups) {
            authorities.add(new SimpleGrantedAuthority(userGroup.getRole().toUpperCase()));
        }

        return authorities;

    }
}

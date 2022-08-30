package com.sergey.accountservice.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class UserDetailsImpl implements UserDetails {

    private final String email;

    private final String password;

    private final List<GrantedAuthority> rolesAndAuthorities;

    private UserDetailsImpl(UserDetailsImplBuilder userDetailsImplBuilder) {
        this.email = userDetailsImplBuilder.email;
        this.password = userDetailsImplBuilder.password;
        this.rolesAndAuthorities = userDetailsImplBuilder.rolesAndAuthorities;
    }


    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @NoArgsConstructor
    public static class UserDetailsImplBuilder {
        private String email;
        private String password;
        private List<GrantedAuthority> rolesAndAuthorities;

        public UserDetailsImplBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDetailsImplBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDetailsImplBuilder rolesAndAuthorities(List<GrantedAuthority> rolesAndAuthorities) {
            this.rolesAndAuthorities = rolesAndAuthorities;
            return this;
        }

        public UserDetailsImpl build() {
            UserDetailsImpl userDetails = new UserDetailsImpl(this);
            return userDetails;
        }

    }
}

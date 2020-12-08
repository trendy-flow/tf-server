package com.trendyflow.authserver.security;

import java.util.Collection;
import java.util.List;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;


import javax.persistence.Entity;


/**
 * spring security user detail 정보
 * @author SUJIN
 *
 */
@Data
@Setter
@Entity
public class UserInformation implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
}
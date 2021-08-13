package com.mouritech.auth.config;

import com.mouritech.auth.dao.User;
import com.mouritech.auth.dao.UserRole;
import com.mouritech.auth.dto.UserAuthDto;
import com.mouritech.auth.dto.UserAuthMainDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private UserAuthMainDto user;


    public CustomUserDetails(UserAuthMainDto user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

       //return Collections.singleton(new SimpleGrantedAuthority(user.getRoleName()));
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (String role: user.getRole()) {
            authorities.add(new SimpleGrantedAuthority(role));
           /* role.getPrivileges().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
                    .forEach(authorities::add);*/
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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

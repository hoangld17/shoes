package com.digiex.spring.boot.demo.auth;

import com.digiex.spring.boot.demo.common.enums.SessionType;
import com.digiex.spring.boot.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author DiGiEx
 */
public class AuthUser implements UserDetails {

    @Getter
    private final String id;
    private final String username;
    private final String password;
    @Getter
    private String name;
    private final boolean enabled;
    @Getter
    private SessionType role;
    @Getter
    private String emailAddress;
    @Getter
    private String lang;


     public AuthUser(User user) {
         this.id = user.getId();
         this.username = user.getUsername();
         this.password = "";
         this.name = user.getFirstName() + " " + user.getLastName();
         this.role = SessionType.USER;
         this.enabled = true;
         this.emailAddress = user.getEmail();
         this.lang = user.getLang();
     }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

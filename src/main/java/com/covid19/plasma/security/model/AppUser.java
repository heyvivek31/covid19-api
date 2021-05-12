package com.covid19.plasma.security.model;

import com.covid19.plasma.enums.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUser extends User {

    private static final long serialVersionUID = -3531439484732724601L;

    private final UserType userType;

    public AppUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                   boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, UserType userType) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userType = userType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UserType getUserType() {
        return userType;
    }
}

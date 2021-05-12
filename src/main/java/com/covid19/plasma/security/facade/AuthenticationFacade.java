package com.covid19.plasma.security.facade;

import com.covid19.plasma.enums.UserType;
import com.covid19.plasma.security.model.AppUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return true;
        }
        return false;
    }

    @Override
    public AppUser getPrincipal() {
        if (isAuthenticated()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (AppUser) auth.getPrincipal();
        }
        return new AppUser("NA", "NA", false, false,
                false, false, new ArrayList<>(), UserType.UNKNOWN);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

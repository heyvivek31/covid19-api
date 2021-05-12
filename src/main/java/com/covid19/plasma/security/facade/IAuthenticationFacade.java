package com.covid19.plasma.security.facade;

import com.covid19.plasma.security.model.AppUser;

public interface IAuthenticationFacade {
    boolean isAuthenticated();
    AppUser getPrincipal();
}

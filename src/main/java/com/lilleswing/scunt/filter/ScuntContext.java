package com.lilleswing.scunt.filter;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.lilleswing.scunt.core.AppUser;

@RequestScoped
public class ScuntContext {

    private AppUser appUser = null;

    @Inject
    public ScuntContext() {
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}

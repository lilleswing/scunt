package com.lilleswing.scunt.filter;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.lilleswing.scunt.core.AppUser;
import com.lilleswing.scunt.db.AuthUserDAO;

@RequestScoped
public class ScuntContext {

    private final AuthUserDAO authUserDAO;
    private String accessToken = null;

    @Inject
    public ScuntContext(final AuthUserDAO authUserDAO) {
        this.authUserDAO = authUserDAO;
    }

    public Optional<AppUser> getAppUser() {
        if(Strings.isNullOrEmpty(accessToken)) {
            return Optional.absent();
        }
        return Optional.of(authUserDAO.authorize(this.accessToken));
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }
}

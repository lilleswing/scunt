package com.lilleswing.scunt.filter;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.lilleswing.scunt.core.AppUser;
import com.lilleswing.scunt.db.AuthUserDAO;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import io.dropwizard.hibernate.UnitOfWork;

@Singleton
public class SecurityFilter implements ContainerRequestFilter {

    private final Provider<ScuntContext> scuntContextProvider;
    private final AuthUserDAO authUserDAO;

    @Inject
    public SecurityFilter(final Provider<ScuntContext> scuntContextProvider,
                          final AuthUserDAO authUserDAO) {
        this.scuntContextProvider = scuntContextProvider;
        this.authUserDAO = authUserDAO;
    }

    @Override
    @UnitOfWork
    public ContainerRequest filter(ContainerRequest containerRequest) {
        final ScuntContext scuntContext = scuntContextProvider.get();
        final String accessToken = containerRequest.getHeaderValue("Authorization");
        scuntContext.setAccessToken(accessToken);
        return containerRequest;
    }
}

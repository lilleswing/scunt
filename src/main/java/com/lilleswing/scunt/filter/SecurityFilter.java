package com.lilleswing.scunt.filter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.lilleswing.scunt.core.AuthUser;
import com.lilleswing.scunt.db.AuthUserDAO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Singleton
public class SecurityFilter implements Filter {

    private final Provider<ScuntContext> scuntContextProvider;
    private final AuthUserDAO authUserDAO;

    @Inject
    public SecurityFilter(final Provider<ScuntContext> scuntContextProvider,
                          final AuthUserDAO authUserDAO) {
        this.scuntContextProvider = scuntContextProvider;
        this.authUserDAO = authUserDAO;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {
        if(!(servletRequest instanceof HttpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String accessToken = request.getHeader("Authorization");
        final AuthUser authUser = authUserDAO.authorize(accessToken);
        if(authUser != null) {
            final ScuntContext scuntContext = scuntContextProvider.get();
            scuntContext.setAppUser(authUser.getAppUser());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}

package com.lilleswing.scunt.db;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lilleswing.scunt.core.AppUser;
import com.lilleswing.scunt.core.AuthUser;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

@Singleton
public class AuthUserDAO extends AbstractDAO<AuthUser> {

    private final SecureRandom secureRandom;

    @Inject
    public AuthUserDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
        this.secureRandom = new SecureRandom();
    }

    public List<AuthUser> list() {
        final Criteria criteria = currentSession().createCriteria(AuthUser.class);
        return list(criteria);
    }

    public AuthUser getById(final long id) {
        return get(id);
    }

    public long create(AuthUser authUser) {
        AppUser appUser = new AppUser();
        authUser.setAppUser(appUser);
        authUser.setAccessToken(new BigInteger(130, secureRandom).toString(32));
        return persist(authUser).getId();
    }

    public boolean exists(String username) {
        final Criteria criteria = currentSession().createCriteria(AuthUser.class);
        criteria.add(Restrictions.eq("username", username));
        final AuthUser authUser = (AuthUser) criteria.uniqueResult();
        if(authUser == null) {
            return false;
        }
        return true;
    }

    public AuthUser authorize(String accessToken) {
        final Criteria criteria = currentSession().createCriteria(AuthUser.class);
        criteria.add(Restrictions.eq("accessToken", accessToken));
        return (AuthUser) criteria.uniqueResult();
    }
}

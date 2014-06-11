package com.lilleswing.scunt.db;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lilleswing.scunt.core.AppUser;
import com.lilleswing.scunt.core.AuthUser;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@Singleton
public class AuthUserDAO extends AbstractDAO<AuthUser> {

    @Inject
    public AuthUserDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
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
        appUser.setAuthUser(authUser);
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

    public AuthUser updateUser(final AuthUser authUser) {
        return this.persist(authUser);
    }
}

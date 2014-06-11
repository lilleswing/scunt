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
public class AppUserDAO extends AbstractDAO<AppUser> {

    @Inject
    public AppUserDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<AppUser> list() {
        final Criteria criteria = currentSession().createCriteria(AppUser.class);
        return list(criteria);
    }

    public AppUser getById(final long id) {
        return get(id);
    }

    public long create(final AppUser appUser) {
        return persist(appUser).getId();
    }

    public boolean exists(String username) {
        final Criteria criteria = currentSession().createCriteria(AppUser.class);
        criteria.add(Restrictions.eq("username", username));
        final AppUser appUser = (AppUser) criteria.uniqueResult();
        if(appUser == null) {
            return false;
        }
        return true;
    }

    public AppUser updateUser(final AppUser appUser) {
        return this.persist(appUser);
    }
}

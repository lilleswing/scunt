package com.lilleswing.scunt.db;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lilleswing.scunt.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

@Singleton
public class UserDAO extends AbstractDAO<User> {

    @Inject
    public UserDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> list() {
        final Criteria criteria = currentSession().createCriteria(User.class);
        return list(criteria);
    }

    public User getById(final long id) {
        return get(id);
    }

    public long create(User user) {
        return persist(user).getId();
    }

    public boolean exists(String username) {
        final Criteria criteria = currentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        final User user = (User) criteria.uniqueResult();
        if(user == null) {
            return false;
        }
        return true;
    }

    public User updateUser(final User user) {
        return this.persist(user);
    }
}

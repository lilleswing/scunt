package com.lilleswing.scunt.db;

import com.lilleswing.scunt.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {

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
}

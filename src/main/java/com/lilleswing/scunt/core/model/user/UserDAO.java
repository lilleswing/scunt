package com.lilleswing.scunt.core.model.user;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> list() {
        return list();
    }

    public User getById(final long id) {
        return get(id);
    }

    public long create(User user) {
        return persist(user).getId();
    }
}

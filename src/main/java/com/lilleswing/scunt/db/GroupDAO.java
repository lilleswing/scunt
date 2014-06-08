package com.lilleswing.scunt.db;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lilleswing.scunt.core.Group;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import java.util.List;

@Singleton
public class GroupDAO extends AbstractDAO<Group> {

    @Inject
    public GroupDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Group> list() {
        final Criteria criteria = currentSession().createCriteria(Group.class);
        return list(criteria);
    }

    public long create(final Group group) {
        return persist(group).getId();
    }

    public Group getById(final long id) {
        return get(id);
    }
}

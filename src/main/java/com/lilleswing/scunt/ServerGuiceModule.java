package com.lilleswing.scunt;

import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;

public class ServerGuiceModule extends AbstractModule {

    private final SessionFactory sessionFactory;

    public ServerGuiceModule(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(sessionFactory);
    }
}

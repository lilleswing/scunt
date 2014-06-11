package com.lilleswing.scunt;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.lilleswing.scunt.core.AppUser;
import com.lilleswing.scunt.core.AuthUser;
import com.lilleswing.scunt.core.Group;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

public class ServerGuiceModule extends AbstractModule {


    public ServerGuiceModule() {
    }

    @Provides
    @Singleton
    SessionFactory providesSessionFactory(final Injector injector) {
        final HibernateBundle hibernateBundle = injector.getInstance(HibernateBundle.class);
        return hibernateBundle.getSessionFactory();
    }

    @Provides
    @Singleton
    HibernateBundle providesHibernateBundle() {
        return new HibernateBundle<ScuntConfiguration>(
                AuthUser.class,
                AppUser.class,
                Group.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(final ScuntConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Override
    protected void configure() {
    }
}

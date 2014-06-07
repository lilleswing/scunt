package com.lilleswing.scunt;

import com.lilleswing.scunt.core.User;
import com.lilleswing.scunt.db.UserDAO;
import com.lilleswing.scunt.health.TemplateHealthCheck;
import com.lilleswing.scunt.resources.HelloWorldResource;
import com.lilleswing.scunt.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class ScuntApplication extends Application<ScuntConfiguration> {
    public static void main(String[] args) throws Exception {
        new ScuntApplication().run(args);
    }

    private final HibernateBundle<ScuntConfiguration> hibernate = new HibernateBundle<ScuntConfiguration>(User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(final ScuntConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "DC Scunt";
    }

    @Override
    public void initialize(Bootstrap<ScuntConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new MigrationsBundle<ScuntConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ScuntConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(ScuntConfiguration configuration,
                    Environment environment) {
        environment.servlets().addFilter("Cross-Origin-Filter", new CrossOriginFilter())
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());
        final HelloWorldResource helloWorldResource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(helloWorldResource);


        final UserResource userResource = new UserResource(userDAO);
        environment.jersey().register(userResource);

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}

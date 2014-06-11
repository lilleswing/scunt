package com.lilleswing.scunt;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.lilleswing.scunt.core.AppUser;
import com.lilleswing.scunt.core.AuthUser;
import com.lilleswing.scunt.core.Group;
import com.lilleswing.scunt.filter.SecurityFilter;
import com.lilleswing.scunt.health.TemplateHealthCheck;
import com.lilleswing.scunt.resources.AuthUserResource;
import com.lilleswing.scunt.resources.GroupResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class ScuntApplication extends Application<ScuntConfiguration> {

    private Injector injector;

    public static void main(String[] args) throws Exception {
        new ScuntApplication().run(args);
    }

    @Override
    public String getName() {
        return "DC Scunt";
    }

    @Override
    public void initialize(Bootstrap<ScuntConfiguration> bootstrap) {
        injector = Guice.createInjector(new ServletModule(), new ServerGuiceModule());
        bootstrap.addBundle(injector.getInstance(HibernateBundle.class));
        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
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
        environment.jersey().setUrlPattern("/api/*");

        // Servlets
        environment.servlets().addFilter("Cross-Origin-Filter", injector.getInstance(CrossOriginFilter.class))
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        environment.servlets().addFilter("Guice-Filter", injector.getInstance(GuiceFilter.class))
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/api/*");
        environment.servlets().addFilter("Security-Filter", injector.getInstance(SecurityFilter.class))
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/api/*");

        // Resources
        final AuthUserResource authUserResource = injector.getInstance(AuthUserResource.class);
        environment.jersey().register(authUserResource);

        final GroupResource groupResource = injector.getInstance(GroupResource.class);
        environment.jersey().register(groupResource);

        // Health Checks
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}

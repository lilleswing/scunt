package com.lilleswing.scunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.lilleswing.scunt.core.Saying;
import com.lilleswing.scunt.core.model.user.User;
import com.lilleswing.scunt.core.model.user.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;

    public UserResource(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public List<User> list() {
        return userDAO.list();
    }

    @GET
    @Path("/create")
    @Timed
    @UnitOfWork
    public User get() {
        final User user = new User();
        user.setEmail("lilleswing@gmail.com");
        user.setPassword("NaClH2O");
        user.setUsername("lilleswing");
        userDAO.create(user);
        return user;
    }
}

package com.lilleswing.scunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.lilleswing.scunt.core.User;
import com.lilleswing.scunt.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
    @Path("/{userId}")
    @Timed
    @UnitOfWork
    public User get(@PathParam("userId") long userId) {
        final User user = userDAO.getById(userId);
        if( user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return user;
    }

    @POST
    @Timed
    @UnitOfWork
    public User create(final User user) {
        final boolean exists = userDAO.exists(user.getUsername());
        if(exists) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        userDAO.create(user);
        return user;
    }
}

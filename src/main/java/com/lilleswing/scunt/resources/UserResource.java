package com.lilleswing.scunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.lilleswing.scunt.core.AppUser;
import com.lilleswing.scunt.core.AuthUser;
import com.lilleswing.scunt.db.AuthUserDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final AuthUserDAO authUserDAO;

    @Inject
    public UserResource(final AuthUserDAO authUserDAO) {
        this.authUserDAO = authUserDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public List<AuthUser> list() {
        return authUserDAO.list();
    }

    @GET
    @Path("/{userId}")
    @Timed
    @UnitOfWork
    public AuthUser get(@PathParam("userId") long userId) {
        final AuthUser authUser = authUserDAO.getById(userId);
        if( authUser == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return authUser;
    }

    @POST
    @Timed
    @UnitOfWork
    public AuthUser create(final AuthUser authUser) {
        final boolean exists = authUserDAO.exists(authUser.getUsername());
        if(exists) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        authUserDAO.create(authUser);
        AppUser appUser = new AppUser();
        appUser.setAuthUser(authUser);
        appUserDao.create(appUser);
        return authUser;
    }
}

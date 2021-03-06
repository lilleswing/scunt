package com.lilleswing.scunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.lilleswing.scunt.core.AuthUser;
import com.lilleswing.scunt.db.AuthUserDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthUserResource {

    private final AuthUserDAO authUserDAO;

    @Inject
    public AuthUserResource(final AuthUserDAO authUserDAO) {
        this.authUserDAO = authUserDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public List<AuthUser> list() {
        return authUserDAO.list();
    }

    /*
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
    */

    @POST
    @Path("/register")
    @Timed
    @UnitOfWork
    public AuthUser register(final AuthUser authUser) {
        final boolean exists = authUserDAO.exists(authUser.getUsername());
        if(exists) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        authUserDAO.create(authUser);
        return authUser;
    }

    /**
     * TODO(LESWING)
     */
    @POST
    @Path("/login")
    @Timed
    @UnitOfWork
    public Map<String, Object> login(final AuthUser authUser) {
        final AuthUser exists = authUserDAO.login(authUser);
        if(exists == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        final Map<String, Object> returns = Maps.newHashMap();
        returns.put("id", String.valueOf(exists.getAppUser().getId()));
        returns.put("token", exists.getAccessToken());
        return returns;
    }
}

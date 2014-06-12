package com.lilleswing.scunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.lilleswing.scunt.core.AppUser;
import com.lilleswing.scunt.core.Group;
import com.lilleswing.scunt.db.AppUserDAO;
import com.lilleswing.scunt.db.GroupDAO;
import com.lilleswing.scunt.filter.ScuntContext;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupResource {

    private final Provider<ScuntContext> scuntContextProvider;
    private final GroupDAO groupDAO;
    private final AppUserDAO appUserDAO;

    @Inject
    public GroupResource(final Provider<ScuntContext> scuntContextProvider,
                         final GroupDAO groupDAO,
                         final AppUserDAO appUserDAO) {
        this.scuntContextProvider = scuntContextProvider;
        this.groupDAO = groupDAO;
        this.appUserDAO = appUserDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public List<Group> list() {
        return groupDAO.list();
    }

    @POST
    @Timed
    @UnitOfWork
    public Group create(final Group group) {
        groupDAO.create(group);
        return group;
    }

    @GET
    @Path("/{groupId}")
    @Timed
    @UnitOfWork
    public Group get(@PathParam("groupId") long groupId) {
        final Group group = groupDAO.getById(groupId);
        if (group == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return group;
    }

    @PUT
    @Path("/{groupId}/user/{userId}")
    @Timed
    @UnitOfWork
    public Group addUser(@PathParam("groupId") long groupId,
                         @PathParam("userId") long userId,
                         final Map<String, String> params) {
        final ScuntContext scuntContext = scuntContextProvider.get();
        if(!scuntContext.getAppUser().isPresent()) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
        final AppUser appUser = scuntContext.getAppUser().get();
        final Group group = groupDAO.getById(groupId);
        final String password = params.get("password");
        if (group == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        if (!password.equals(group.getPassword()) || appUser.getId() != userId) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
        appUser.setGroup(group);
        group.getAppUsers().add(appUser);
        appUserDAO.updateUser(appUser);
        return group;
    }
}

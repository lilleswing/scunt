package com.lilleswing.scunt.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.lilleswing.scunt.core.Group;
import com.lilleswing.scunt.core.User;
import com.lilleswing.scunt.db.GroupDAO;
import com.lilleswing.scunt.db.UserDAO;
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

    private final GroupDAO groupDAO;
    private final UserDAO userDAO;

    @Inject
    public GroupResource(final GroupDAO groupDAO,
                         final UserDAO userDAO) {
        this.groupDAO = groupDAO;
        this.userDAO = userDAO;
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
        if( group == null) {
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
        final Group group = groupDAO.getById(groupId);
        final String password = params.get("password");
        if( group == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        final User user = userDAO.getById(userId);
        if( user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        if(!password.equals(group.getPassword())) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
        user.setGroup(group);
        group.getUsers().add(user);
        userDAO.updateUser(user);
        return group;
    }
}

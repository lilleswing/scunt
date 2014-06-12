package com.lilleswing.scunt.core;


import com.google.inject.Inject;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="app_user")
public class AppUser implements DbModel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_id_seq")
    @SequenceGenerator(name = "app_user_id_seq", sequenceName = "app_user_id_seq", allocationSize = 1)
    private long id;

    @JoinColumn(name="group_id")
    @ManyToOne(targetEntity = Group.class, cascade = CascadeType.ALL)
    private Group group;

    @JoinColumn(name="auth_user_id")
    @OneToOne(targetEntity = AuthUser.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private AuthUser authUser;

    public AppUser() {
    }

    public AppUser(final AppUser o) {
        this.id = o.getId();
        this.group = o.getGroup();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }
}

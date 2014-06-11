package com.lilleswing.scunt.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Sets;
import com.lilleswing.scunt.core.util.IdSerializer;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="scunt_group")
public class Group implements DbModel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scunt_group_id_seq")
    @SequenceGenerator(name = "scunt_group_id_seq", sequenceName = "scunt_group_id_seq", allocationSize = 1)
    public long id;

    @Column(name="name")
    public String name;

    @Column(name="password")
    public String password;

    @OneToMany(targetEntity = AppUser.class, cascade = CascadeType.ALL, mappedBy = "group")
    @JsonSerialize(using = IdSerializer.class)
    @JsonProperty(value = "users")
    public Set<AppUser> appUsers = Sets.newHashSet();

    public Group() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
}

package com.lilleswing.scunt.core;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="scunt_user")
public class User implements DbModel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scunt_user_id_seq")
    @SequenceGenerator(name = "scunt_user_id_seq", sequenceName = "scunt_user_id_seq", allocationSize = 1)
    public long id;

    @Column(name="username")
    public String username;

    @Column(name="email")
    public String email;

    @Column(name="password")
    public String password;

    @JoinColumn(name="group_id")
    @ManyToOne(targetEntity = Group.class, cascade = CascadeType.ALL)
    public Group group;

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

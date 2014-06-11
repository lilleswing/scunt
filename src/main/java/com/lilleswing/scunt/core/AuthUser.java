package com.lilleswing.scunt.core;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="auth_user")
public class AuthUser implements DbModel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_user_id_seq")
    @SequenceGenerator(name = "auth_user_id_seq", sequenceName = "auth_user_id_seq", allocationSize = 1)
    public long id;

    @Column(name="username")
    public String username;

    @Column(name="email")
    public String email;

    @Column(name="password")
    public String password;

    @Column(name="access_token")
    public String accessToken;

    @JoinColumn(name="auth_user_id")
    @OneToOne(targetEntity = AppUser.class, cascade = CascadeType.ALL, mappedBy = "authUser")
    public AppUser appUser;

    public AuthUser() {

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

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

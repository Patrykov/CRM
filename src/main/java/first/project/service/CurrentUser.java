package first.project.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {

    private final first.project.entity.User user;

    public CurrentUser(String username, String password, Collection<?
            extends GrantedAuthority> authorities,
                       first.project.entity.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public first.project.entity.User getUser() {
        return user;
    }
}
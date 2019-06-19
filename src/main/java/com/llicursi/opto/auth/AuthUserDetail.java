package com.llicursi.opto.auth;

import com.llicursi.opto.domainobject.UserDO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Session user to be retrieved by the Authentication Principal
 */
public class AuthUserDetail implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String name;
    private final String surname;
    private final String roles;
    private List<SimpleGrantedAuthority> authorities;

    public AuthUserDetail(UserDO userDO) {
        this.id = userDO.getId();
        this.username = userDO.getEmail();
        this.email = userDO.getEmail();
        this.password = userDO.getPassword();
        this.name = userDO.getName();
        this.surname = userDO.getSurname();
        this.roles = userDO.getRole();
        this.authorities = AuthUtils.parseAuthorities(userDO.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AuthUserDetail{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", authorities=" + authorities +
            '}';
    }

}

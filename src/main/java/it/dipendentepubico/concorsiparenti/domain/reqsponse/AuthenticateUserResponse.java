package it.dipendentepubico.concorsiparenti.domain.reqsponse;

import it.dipendentepubico.concorsiparenti.domain.auth.UserDetailsImpl;
import it.dipendentepubico.concorsiparenti.rest.auth.model.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class AuthenticateUserResponse {
    private String jwt;
    private UserDetailsImpl userDetails;
    private List<Role> roles;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserDetailsImpl getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

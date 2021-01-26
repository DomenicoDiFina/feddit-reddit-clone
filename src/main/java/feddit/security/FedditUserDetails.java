package feddit.security;

import feddit.model.Role;
import feddit.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class FedditUserDetails implements UserDetails {

    private User user;

    public FedditUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getDescription()))
                .collect(Collectors.toList());
    }

    /*** TO CHECK IF IS CORRECT ***/

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    //i don't know if this is secure or it need to be encapsulated (see below)
    /*public User getUser() {
        return this.user;
    }*/

    public String getFirstName() {
        return this.user.getFirstName();
    }

    public String getLastName() {
        return this.user.getLastName();
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    public Date getBirthDate() {
        return this.user.getBirthDate();
    }

}

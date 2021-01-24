package feddit.security;

import feddit.model.Role;
import feddit.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import  java.sql.Date;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return authorities;
    }

    public String getFullName() {
        return user.getName() + " " + user.getSurname();
    }

    //i don't know if this is secure or it need to be encapsulated (see below)
    /*public User getUser() {
        return this.user;
    }*/


    public String getName() {
        return this.user.getName();
    }

    public String getSurname() {
        return this.user.getSurname();
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    public Date getBirth() {
        return this.user.getBirth();
    }

}

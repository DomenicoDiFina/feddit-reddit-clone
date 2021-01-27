package feddit.services;


import feddit.model.User;
import feddit.security.FedditUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class AuthService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        /*if(user.getRoles().contains(new Role("ADMIN"))) {
            //TODO find the right exception
            throw new UsernameNotFoundException("User is an admnistrator");
        }*/
        return new FedditUserDetails(user);
    }

}

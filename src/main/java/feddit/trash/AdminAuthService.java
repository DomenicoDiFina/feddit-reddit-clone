package feddit.trash;/*package feddit.services;


import feddit.model.Role;
import feddit.model.User;
import feddit.repositories.UserRepository;
import feddit.security.FedditUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class AdminAuthService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Admin not found");
        }
        if(!user.getRoles().contains(new Role("ADMIN"))) {
            //TODO find the right exception
            throw new UsernameNotFoundException("This is an user account");
        }
        return new FedditUserDetails(user);
    }
}*/

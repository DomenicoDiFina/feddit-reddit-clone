package feddit.services;

import feddit.model.User;
import feddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User findUserByUsername(String username){

        System.out.println("Ciao " + username);
        User user = userRepo.findByUsername(username);

        System.out.println(user.getUsername());

        return user;
    }
}

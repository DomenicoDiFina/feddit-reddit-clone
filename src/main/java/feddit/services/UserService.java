package feddit.services;

import feddit.model.User;
import feddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findByUsername(String username){

        User user = repository.findByUsername(username);

        return user;
    }

    public void save(User user) {
        repository.save(user);
    }
}

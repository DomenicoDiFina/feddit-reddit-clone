package feddit.services;

import feddit.model.User;
import feddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User findByUsername(String username) {
        try {
            return this.repository.findByUsername(username);
        } catch (DataAccessException | UsernameNotFoundException dataAccessException){
            return null;
        }
    }

    public boolean save(User user) {
        try {
            repository.save(user);
            return true;
        } catch (DataAccessException dataAccessException) {
            return false;
        }
    }

}

package feddit.services;

import feddit.model.User;
import feddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements ForumService<User> {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        try {
            return this.userRepository.findByUsername(username);
        } catch (DataAccessException | UsernameNotFoundException dataAccessException) {
            return null;
        }
    }

    @Override
    public CrudRepository<User, Long> getCrudRepository() {
        return this.userRepository;
    }
}

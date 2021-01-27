package feddit.repositories;

import feddit.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    default Optional<User> findByUsername(String username) {
        return StreamSupport
                .stream(this.findAll().spliterator(), false)
                .filter(user -> user.getUsername().equals(username))
                .findAny();
    }

}

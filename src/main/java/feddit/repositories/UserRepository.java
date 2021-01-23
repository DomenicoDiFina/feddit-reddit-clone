package feddit.repositories;

import feddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User SET password = ?2 WHERE username = ?1")
    void changeUserPassword(String username, String newPassword);

}

package feddit.repositories;

import feddit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.role = ?1")
    Role findByName(String role);
}

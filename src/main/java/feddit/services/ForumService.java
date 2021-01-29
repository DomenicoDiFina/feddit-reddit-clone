package feddit.services;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface ForumService<T> {

    CrudRepository<T, Long> getCrudRepository();

    default Boolean save(T t) {
        try {
            this.getCrudRepository().save(t);
            return true;
        } catch (DataAccessException dataAccessException) {
            return false;
        }
    }

}

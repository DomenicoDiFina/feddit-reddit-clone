package feddit.services;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface ForumService<T> {

    CrudRepository<T, Long> getCrudRepository();

    default T findById(long id) {
        return this.getCrudRepository().findById(id).get();
    }

    default boolean deleteById(long id) {
        try{
            this.getCrudRepository().deleteById(id);
            return true;
        } catch (DataAccessException dataAccessException) {
            return false;
        }
    }

    default boolean save(T t) {
        try {
            this.getCrudRepository().save(t);
            return true;
        } catch (DataAccessException dataAccessException) {
            return false;
        }
    }

}

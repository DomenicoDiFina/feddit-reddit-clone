package feddit.services;

import feddit.model.Comment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ForumService<T> {

    CrudRepository<T, Long> getCrudRepository();

    default T findById(long id) {
        Optional<T> optional = this.getCrudRepository().findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            //TODO new Exception
            return null;
        }
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

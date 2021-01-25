package feddit.repositories;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.user = ?1")
    List<Comment> findAllByUser(User user);

    @Query("SELECT c FROM Comment c WHERE c.post = ?1")
    List<Comment> findAllByPost(Post post);

}

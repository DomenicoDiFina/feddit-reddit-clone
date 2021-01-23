package feddit.repositories;

import feddit.model.Comment;
import feddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.userID = ?1")
    List<Comment> findAllByUser(long userID);

    @Query("SELECT c FROM Comment c WHERE c.postID = ?1")
    List<Post> findAllByPost(long postID);

}

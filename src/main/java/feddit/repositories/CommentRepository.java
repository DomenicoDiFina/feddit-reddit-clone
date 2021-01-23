package feddit.repositories;

import feddit.model.Comment;
import feddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

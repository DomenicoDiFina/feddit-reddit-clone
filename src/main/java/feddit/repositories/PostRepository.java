package feddit.repositories;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
}

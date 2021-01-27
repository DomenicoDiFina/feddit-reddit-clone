package feddit.repositories;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface PostRepository extends CrudRepository<Post, Long> {

    default List<Post> findAllByUser(User user) {
        return StreamSupport
                .stream(this.findAll().spliterator(), false)
                .filter(post -> post.getUser().equals(user))
                .collect(Collectors.toList());
    }
}

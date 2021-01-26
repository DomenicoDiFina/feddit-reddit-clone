package feddit.repositories;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    default List<Comment> findAllByUser(User user) {
        return StreamSupport
                .stream(this.findAll().spliterator(), false)
                .filter(comment -> comment.getUser().equals(user))
                .collect(Collectors.toList());
    }

    default List<Comment> findAllByPost(Post post) {
        return StreamSupport
                .stream(this.findAll().spliterator(), false)
                .filter(comment -> comment.getPost().equals(post))
                .collect(Collectors.toList());
    }

}


package feddit.repositories;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import feddit.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

    default Optional<Vote> findByPostAndUser(User user, Post post) {
        if (this.findAll() == null) return Optional.empty();
        return StreamSupport
                .stream(this.findAll().spliterator(), false)
                .filter(vote -> vote.getUser().equals(user) && vote.getPost() != null && vote.getPost().equals(post))
                .findAny();
    }

    default Optional<Vote> findByCommentAndUser(User user, Comment comment) {
        if (this.findAll() == null) return Optional.empty();
        return StreamSupport
                .stream(this.findAll().spliterator(), false)
                .filter(vote -> vote.getUser().equals(user) && vote.getComment() != null && vote.getComment().equals(comment))
                .findAny();
    }

}

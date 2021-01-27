package feddit.repositories;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import feddit.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

    default Optional<Vote> findByPostAndUser(User user, Post post){
        List<Vote> votes = (List<Vote>) this.findAll();
        for(Vote vote : votes){
            System.out.println("User: " + vote.getUser());
            System.out.println("Post: " + vote.getPost());
        }

        return StreamSupport
                .stream(this.findAll().spliterator(), false)
                .filter(vote -> vote.getUser().equals(user) && vote.getPost().equals(post))
                .findAny();
    }

    default Optional<Vote> findByCommentAndUser(User user, Comment comment){
        return StreamSupport
                .stream(this.findAll().spliterator(), false)
                .filter(vote -> vote.getUser().equals(user) && vote.getComment().equals(comment))
                .findAny();
    }
}

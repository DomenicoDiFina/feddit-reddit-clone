package feddit.services;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import feddit.model.Vote;
import feddit.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService implements ForumService<Vote> {

    @Autowired
    private VoteRepository voteRepository;

    public Optional<Vote> findByPostAndUser(User user, Post post){
        return this.voteRepository.findByPostAndUser(user,post);
    }

    public Optional<Vote> findByCommentAndUser(User user, Comment comment) {
        return this.voteRepository.findByCommentAndUser(user,comment);
    }

    public boolean remove(Vote vote) {
        try{
            this.voteRepository.delete(vote);
            return true;
        } catch (DataAccessException dataAccessException) {
            return false;
        }


    }

    @Override
    public CrudRepository<Vote, Long> getCrudRepository() {
        return this.voteRepository;
    }
}

package feddit.services;

import feddit.model.Comment;
import feddit.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService implements ForumService<Comment> {

    @Autowired
    private CommentRepository commentRepository;

    public Comment findById(long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            //TODO new Exception
            return null;
        }
    }

    public boolean deleteById(long id) {
        try{
            this.commentRepository.deleteById(id);
            return true;
        }catch (DataAccessException dataAccessException) {
            return false;
        }

    }

    @Override
    public boolean save(Comment comment) {
        try {
            commentRepository.save(comment);
            return true;
        } catch (DataAccessException dataAccessException) {
            return false;
        }
    }

}

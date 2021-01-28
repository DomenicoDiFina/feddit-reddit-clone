package feddit.services;

import feddit.model.Comment;
import feddit.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

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
    
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

}

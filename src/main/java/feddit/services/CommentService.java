package feddit.services;

import feddit.model.Comment;
import feddit.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

}

package feddit.services;

import feddit.model.Comment;
import feddit.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service
public class CommentService implements ForumService<Comment> {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CrudRepository<Comment, Long> getCrudRepository() {
        return this.commentRepository;
    }
}

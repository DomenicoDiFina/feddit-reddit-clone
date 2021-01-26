package feddit.services;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import feddit.repositories.CommentRepository;
import feddit.repositories.PostRepository;
import feddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class CommentService {

    /*** TO CHECK IF IS CORRECT ***/

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    List<Comment> findAllByUser(long userID) throws Exception {
        User user = userRepository.findById(userID).orElseThrow(() -> new IllegalArgumentException("User didn't find"));
        return user.getComments();
    }

    List<Comment> findAllByPost(long postID) throws Exception {
        Post post = postRepository.findById(postID).orElseThrow(() -> new IllegalArgumentException("User didn't find"));
        return post.getComments();
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

}

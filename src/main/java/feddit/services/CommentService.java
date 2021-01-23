package feddit.services;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import feddit.repositories.CommentRepository;
import feddit.repositories.PostRepository;
import feddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    List<Comment> findAllByUser(long userID) throws Exception {
        User user = userRepository.findById(userID).orElseThrow(() -> new Exception("User didn't find"));
        return commentRepository.findAllByUser(user)
                .stream()
                .collect(toList());
    }


    List<Comment> findAllByPost(long postID) throws Exception {
        Post post = postRepository.findById(postID).orElseThrow(() -> new Exception("User didn't find"));
        return commentRepository.findAllByPost(post)
                .stream()
                .collect(toList());
    }
}

package feddit.services;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return (List<Post>) this.postRepository.findAll();
    }

    public int getNComments(Post post) {
        List<Comment> comments = post.getComments();
        return comments.size();
    }


    public Post save(Post post) {
        return this.postRepository.save(post);
    }

}

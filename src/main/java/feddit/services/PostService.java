package feddit.services;

import feddit.model.Post;
import feddit.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(long id) {
        return this.postRepository.findById(id).orElse(null);
    }

    public List<Post> findAll() {
        return (List<Post>) this.postRepository.findAll();
    }

    public void remove(long id){
        this.postRepository.deleteById(id);
    }


    public Post save(Post post) {
        return this.postRepository.save(post);
    }

}

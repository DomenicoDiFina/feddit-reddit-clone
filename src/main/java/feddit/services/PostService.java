package feddit.services;

import feddit.model.Post;
import feddit.model.User;
import feddit.repositories.PostRepository;
import feddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    List<Post> findAllByUser(long userID) throws Exception {
        User user = userRepository.findById(userID).orElseThrow(() -> new Exception("User didn't find"));
        return postRepository.findAllByUser(user);
    }


    List<Post> findAllByUsername(String username){
        User user = userRepository.findByUsername(username);

        return postRepository.findAllByUser(user);
    }

    public Post savePost(Post post){
        return postRepository.save(post);
    }




}

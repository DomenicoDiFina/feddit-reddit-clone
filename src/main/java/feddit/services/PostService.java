package feddit.services;


import feddit.model.Post;
import feddit.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService implements ForumService<Post> {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return (List<Post>) this.postRepository.findAll();
    }

    @Override
    public CrudRepository<Post, Long> getCrudRepository() {
        return this.postRepository;
    }

}

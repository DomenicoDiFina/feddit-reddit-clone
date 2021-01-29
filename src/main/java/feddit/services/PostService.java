package feddit.services;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
public class PostService implements ForumService<Post> {

    @Autowired
    private PostRepository postRepository;

    public Post findById(long id) {
        return this.postRepository.findById(id).orElse(null);
    }

    public List<Post> findAll() {
        return (List<Post>) this.postRepository.findAll();
    }

    public boolean remove(long id){
        try{
            this.postRepository.deleteById(id);
            return true;
        } catch (DataAccessException dataAccessException) {
            return false;
        }

    }

    @Override
    public CrudRepository<Post, Long> getCrudRepository() {
        return this.postRepository;
    }
}

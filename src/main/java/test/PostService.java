package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private testRepo repository;

    public List<Post> findAll() {
        List<Post> articles = (List<Post>) repository.findAll();
        return articles;
    }

    public void save(Post article) {
        repository.save(article);
    }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Post article) {
        repository.delete(article);
    }

}
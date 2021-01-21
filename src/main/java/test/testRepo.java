package test;

import org.springframework.data.repository.CrudRepository;

public interface testRepo extends CrudRepository<Post, Long> {
}

package feddit.services;

import feddit.model.Vote;
import feddit.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService implements ForumService<Vote> {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public CrudRepository<Vote, Long> getCrudRepository() {
        return this.voteRepository;
    }

}

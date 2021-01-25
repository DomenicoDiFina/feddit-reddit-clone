package feddit.services;

import feddit.model.Role;
import feddit.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository repository;

    public Role findByName(String name) {
        return repository.findByName(name);
    }

}

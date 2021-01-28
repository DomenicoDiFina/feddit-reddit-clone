package feddit.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "roles")
public class Role extends DatabaseObject {

    @Column(name = "description")
    private String description;

    public Role() {
    }

    @Override
    public String getClazz() {
        return "Role";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
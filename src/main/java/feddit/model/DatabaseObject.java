package feddit.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@MappedSuperclass
public abstract class DatabaseObject {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "creation_date")
    protected Date creationDate;

    public DatabaseObject() {
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}

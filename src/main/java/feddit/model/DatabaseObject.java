package feddit.model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class DatabaseObject {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "creation_date", nullable = false)
    protected Date creationDate;

    public DatabaseObject() {
        this.creationDate = new Date();
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

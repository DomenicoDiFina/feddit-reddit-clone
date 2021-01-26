package feddit.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends ForumObject {

    @NotEmpty
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

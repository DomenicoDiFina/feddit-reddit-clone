package feddit.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends ForumObject {

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "post", orphanRemoval=true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Vote> votes;

    public Post() {
    }

    @Override
    public String getClazz() {
        return "Post";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getNumberOfComments() {
        List<Comment> comments = this.getComments();
        return comments.size();
    }

}

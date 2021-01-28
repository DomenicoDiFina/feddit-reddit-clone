package feddit.model;

import feddit.model.hierarchy.ForumObject;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return super.toString() +
                ", Title: " + this.title +
                ", Comments: " + this.comments +
                ", Votes: " + this.votes;
    }
}

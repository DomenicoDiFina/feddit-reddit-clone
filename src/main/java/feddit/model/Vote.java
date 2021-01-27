package feddit.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "votes")
public class Vote extends DatabaseObject{

    public Vote(){
        this.creationDate = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}

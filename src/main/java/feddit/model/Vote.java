package feddit.model;

import feddit.model.hierarchy.DatabaseObject;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class Vote extends DatabaseObject {

    public Vote() {}

    @Override
    public String getClazz() {
        return "Vote";
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

    @Override
    public String toString() {
        return super.toString() +
                ",\n\tPost: " + this.post +
                ",\n\tUser: " + this.user +
                ",\n\tType: " + this.type +
                ",\n\tComment: " + this.comment;
    }
}

package feddit.model;

import feddit.model.hierarchy.ForumObject;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment extends ForumObject {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments;

    public Comment() {
    }

    @Override
    public String getClazz() {
        return "Comment";
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
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
                ", Post: " + this.post +
                ", Comment: " + this.comment +
                ", Comments: " + this.comments;
    }
}


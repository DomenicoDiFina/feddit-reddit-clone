package feddit.model;

import feddit.model.hierarchy.ForumObject;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment extends ForumObject {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<Vote> votes;

    public Comment() {}

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
        this.comments.sort(Comparator.comparingInt(c -> c.getDownVotes() - c.getUpVotes()));
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

}


/*package feddit.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import feddit.User;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long voteID;

    @NotEmpty
    @Column(name = "vote")
    private int vote;

    public long getVoteID() {
        return voteID;
    }

    public void setVoteID(long voteID) {
        this.voteID = voteID;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;

    @Column(name = "creationDate")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "commentID", referencedColumnName = "commentID")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "postID", referencedColumnName = "postID")
    private Post post;

}*/

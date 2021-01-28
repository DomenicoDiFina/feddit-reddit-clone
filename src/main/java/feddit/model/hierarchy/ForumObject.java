package feddit.model.hierarchy;

import feddit.model.Comment;
import feddit.model.User;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public abstract class ForumObject extends DatabaseObject {
    
    @Column(name = "content", columnDefinition = "TEXT")
    protected String content;

    @Column(name = "up_votes")
    protected int upVotes;

    @Column(name = "down_votes")
    protected int downVotes;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected User user;

    public ForumObject() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public abstract List<Comment> getComments();

    public int getCommentsNumber() {
        return this.getComments().stream().mapToInt(comment -> comment.getCommentsNumber() + 1).sum();
    }

    @Override
    public String toString() {
        return super.toString() +
                ",\n\tContent: " + this.content +
                ",\n\tUp votes: " + this.upVotes +
                ",\n\tDown votes: " + this.downVotes +
                ",\n\tUser: " + this.user;
    }
}

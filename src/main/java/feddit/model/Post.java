package feddit.model;

import feddit.model.hierarchy.ForumObject;

import javax.persistence.*;

import java.util.ArrayList;
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

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public boolean hasDownVoteByUser(long userID){
        List<Vote> votes = this.getVotes();
        for(Vote vote : votes){
            if(vote.getUser().getId() == userID && vote.getType().equals("DOWNVOTE"))
                return true;
        }
        return false;
    }

    public boolean hasUpVoteByUser(long userID){
        List<Vote> votes = this.getVotes();
        for(Vote vote : votes){
            if(vote.getUser().getId() == userID && vote.getType().equals("UPVOTE"))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() +
                ",\n\tTitle: " + this.title +
                ",\n\tComments: " + this.comments +
                ",\n\tVotes: " + this.votes;
    }

}

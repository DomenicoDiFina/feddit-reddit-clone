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

    @ManyToOne()
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<Vote> votes;

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

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return super.toString() +
                ",\n\tPost: " + this.post +
                ",\n\tComment: " + this.comment +
                ",\n\tComments: " + this.comments;
    }

    public boolean hasDownVoteByUser(long userID){
        List<Vote> votes = this.getVotes();
        //System.out.println(votes.size());
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
}


package feddit.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long voteID;

    @NotEmpty
    @Column(name = "vote")
    private int vote;

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

}

package feddit.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentID")
    private long commentID;

    @NotEmpty
    @Column(name = "content")
    private String content;

    @Column(name = "creationDate")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "postID", referencedColumnName = "postID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;

    @OneToMany
    private List<Comment> comments;

    @Column(name = "voteCount")
    private int voteCount;

}

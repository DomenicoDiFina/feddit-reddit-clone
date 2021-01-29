package feddit.controllers;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import feddit.model.Vote;
import feddit.security.FedditUserDetails;
import feddit.services.CommentService;
import feddit.services.PostService;
import feddit.services.UserService;
import feddit.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class VoteController {

    @Autowired
    private UserService userService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/votePost/{id}")
    public String processPostVote(@AuthenticationPrincipal FedditUserDetails userDetails,
                                       RedirectAttributes redirectAttributes,
                                      @PathVariable long id, Vote vote, Model model) throws Exception {
        User user = userService.findByUsername(userDetails.getUsername());
        Post post = postService.findById(id);
        Optional<Vote> optVote = voteService.findByPostAndUser(user, post);

        if (optVote.isPresent() &&
                optVote.get().getType()
                        .equals(vote.getType())) {
            voteService.remove(optVote.get());

            if(vote.getType().equals("UPVOTE"))
                post.setUpVotes(post.getUpVotes() - 1);
            else
                post.setDownVotes(post.getDownVotes() - 1);

        }
        else if (optVote.isPresent() && !optVote.get().getType().equals(vote.getType())){
            voteService.remove(optVote.get());

            if(vote.getType().equals("UPVOTE"))
                post.setUpVotes(post.getUpVotes() + 2);
            else
                post.setDownVotes(post.getDownVotes() + 2);

            vote.setPost(post);
            vote.setUser(user);

            if (voteService.save(vote)) {
                redirectAttributes.addFlashAttribute("voteAdded", "Vote added successfully");
            } else {
                redirectAttributes.addFlashAttribute("voteError", "An error occured.");
            }
        }
        else {
            if("UPVOTE".equals(vote.getType())) {
                post.setUpVotes(post.getUpVotes() + 1);
            } else if("DOWNVOTE".equals(vote.getType())){
                post.setDownVotes(post.getDownVotes() + 1);
            }

            vote.setPost(post);
            vote.setUser(user);

            if (voteService.save(vote)) {
                redirectAttributes.addFlashAttribute("voteAdded", "Vote added successfully");
            } else {
                redirectAttributes.addFlashAttribute("voteError", "An error occured.");
            }
        }

        if(postService.save(post)) {
        } else {
            redirectAttributes.addFlashAttribute("postError", "An error occured.");
        }

        return "redirect:/";
    }


    @PostMapping("/voteComment/{id}")
    public String processCommentVote(@AuthenticationPrincipal FedditUserDetails userDetails,
                                     RedirectAttributes redirectAttributes,
                                     @PathVariable long id, @RequestParam("post") long postId, Vote vote, Model model) throws Exception {
        System.out.println("Ciao, sono qui");
        User user = userService.findByUsername(userDetails.getUsername());
        Comment comment = commentService.findById(id);
        Optional<Vote> optVote = voteService.findByCommentAndUser(user, comment);

        if (optVote.isPresent() &&
                optVote.get().getType()
                        .equals(vote.getType())) {
            voteService.remove(optVote.get());

            if(vote.getType().equals("UPVOTE"))
                comment.setUpVotes(comment.getUpVotes() - 1);
            else
                comment.setDownVotes(comment.getDownVotes() - 1);

        }
        else if (optVote.isPresent() && !optVote.get().getType().equals(vote.getType())){
            voteService.remove(optVote.get());

            if(vote.getType().equals("UPVOTE"))
                comment.setUpVotes(comment.getUpVotes() + 2);
            else
                comment.setDownVotes(comment.getDownVotes() + 2);

            vote.setComment(comment);
            vote.setUser(user);

            if (voteService.save(vote)) {
                redirectAttributes.addFlashAttribute("voteAdded", "Vote added successfully");
            } else {
                redirectAttributes.addFlashAttribute("voteError", "An error occured.");
            }
        }
        else {

            if("UPVOTE".equals(vote.getType())) {
                comment.setUpVotes(comment.getUpVotes() + 1);
            } else if("DOWNVOTE".equals(vote.getType())){
                comment.setDownVotes(comment.getDownVotes() + 1);
            }

            vote.setComment(comment);
            vote.setUser(user);

            if (voteService.save(vote)) {
                redirectAttributes.addFlashAttribute("voteAdded", "Vote added successfully");
            } else {
                redirectAttributes.addFlashAttribute("voteError", "An error occured.");
            }
        }

        if(commentService.save(comment)) {
        } else {
            redirectAttributes.addFlashAttribute("commentError", "An error occured.");
        }

        Post post = postService.findById(postId);
        System.out.println(post.getTitle());
        model.addAttribute("post", postService.findById(postId));

        return "redirect:/view_post?id="+postId;
    }

}

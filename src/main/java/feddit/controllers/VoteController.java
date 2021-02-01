package feddit.controllers;

import feddit.model.*;
import feddit.security.FedditUserDetails;
import feddit.services.CommentService;
import feddit.services.PostService;
import feddit.services.UserService;
import feddit.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/vote/{id}")
    public String processPostVote(@AuthenticationPrincipal FedditUserDetails userDetails,
                                        RedirectAttributes redirectAttributes,
                                        @PathVariable long id,
                                        @RequestParam("typeObj") String typeObject,
                                        @RequestParam("post") long postId,
                                        @RequestParam("place") String place,
                                        Vote vote,
                                        Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        Optional<Vote> optVote = Optional.empty();
        Post post = null;
        Comment comment = null;

        if(typeObject.equals("POST")){
            post = postService.findById(id);

            optVote = voteService.findByPostAndUser(user, post);
            //Optional<Vote> optVote = post.getVotes().stream().filter(v -> v.getUser().equals(user)).findAny();
        }
        else{
            comment = commentService.findById(id);

            optVote = voteService.findByCommentAndUser(user, comment);
            //Optional<Vote> optVote = comment.getVotes().stream().filter(v -> v.getUser().equals(user)).findAny();
        }


        ResultObject result = null;

        if (optVote.isPresent() &&
                optVote.get().getType()
                        .equals(vote.getType())) {

            if (!voteService.deleteById(optVote.get().getId())) {
                result = new ResultObject("E10", "error", "An error occured.");
            }

            if(vote.getType().equals(Vote.UPVOTE)) {
                if(typeObject.equals("POST"))
                    post.setUpVotes(post.getUpVotes() - 1);
                else
                    comment.setUpVotes(comment.getUpVotes() - 1);
            } else {
                if(typeObject.equals("POST"))
                    post.setDownVotes(post.getDownVotes() - 1);
                else
                    comment.setDownVotes(comment.getDownVotes() - 1);
            }

        }
        else if (optVote.isPresent() && !optVote.get().getType().equals(vote.getType())){
            if (!voteService.deleteById(optVote.get().getId())) {
                result = new ResultObject("E11", "error", "An error occured.");
            }

            if(vote.getType().equals(Vote.UPVOTE)) {
                if (typeObject.equals("POST")) {
                    post.setUpVotes(post.getUpVotes() + 1);
                    post.setDownVotes(post.getDownVotes() - 1);
                } else {
                    comment.setUpVotes(comment.getUpVotes() + 1);
                    comment.setDownVotes(comment.getDownVotes() - 1);
                }
                vote = new Vote();
                vote.setType(Vote.UPVOTE);
            }

            else{
                if (typeObject.equals("POST")) {
                    post.setUpVotes(post.getUpVotes() - 1);
                    post.setDownVotes(post.getDownVotes() + 1);
                } else {
                    comment.setUpVotes(comment.getUpVotes() - 1);
                    comment.setDownVotes(comment.getDownVotes() + 1);
                }
                vote = new Vote();
                vote.setType(Vote.DOWNVOTE);
            }

            if(typeObject.equals("POST"))
                vote.setPost(post);
            else
                vote.setComment(comment);

            vote.setUser(user);
            if (!voteService.save(vote)) {
                result = new ResultObject("E12", "error", "An error occured.");
            }
        }
        else {
            if(Vote.UPVOTE.equals(vote.getType())) {
                if(typeObject.equals("POST"))
                    post.setUpVotes(post.getUpVotes() + 1);
                else
                    comment.setUpVotes(comment.getUpVotes() + 1);

                vote = new Vote();
                vote.setType(Vote.UPVOTE);
            } else if(Vote.DOWNVOTE.equals(vote.getType())){
                if(typeObject.equals("POST"))
                    post.setDownVotes(post.getDownVotes() + 1);
                else
                    comment.setDownVotes(comment.getDownVotes() + 1);

                vote = new Vote();
                vote.setType(Vote.DOWNVOTE);
            }

            if(typeObject.equals("POST"))
                vote.setPost(post);
            else
                vote.setComment(comment);

            vote.setUser(user);

            if (!voteService.save(vote)) {
                result = new ResultObject("E13", "error", "An error occured.");
            }
        }

        if(typeObject.equals("POST")) {
            if (!postService.save(post)) {
                result = new ResultObject("E14", "error", "An error occured.");
            }
        }
        else{
            if (!commentService.save(comment)) {
                result = new ResultObject("E19", "error", "An error occured.");
            }
        }

        if(result != null) {
            redirectAttributes.addFlashAttribute("postResult", result);
        }

        if(typeObject.equals("POST")) {
            if(place.equals("INDEX"))
                return "redirect:/";
            else
                return "redirect:/view_post?id=" + postId;
        }
        else {
            model.addAttribute("post", postService.findById(postId));
            return "redirect:/view_post?id=" + postId;
        }
    }


    /*@PostMapping("/voteComment/{id}")
    public String processCommentVote(@AuthenticationPrincipal FedditUserDetails userDetails,
                                     RedirectAttributes redirectAttributes,
                                     @PathVariable long id,
                                     @RequestParam("post") long postId,
                                     Vote vote,
                                     Model model) {
        ResultObject result = null;

        User user = userService.findByUsername(userDetails.getUsername());
        Comment comment = commentService.findById(id);

        Optional<Vote> optVote = voteService.findByCommentAndUser(user, comment);
        //Optional<Vote> optVote = comment.getVotes().stream().filter(v -> v.getUser().equals(user)).findAny();

        if (optVote.isPresent() &&
                optVote.get().getType()
                        .equals(vote.getType())) {

            if(!voteService.deleteById(optVote.get().getId())) {
                result = new ResultObject("E15", "error", "An error occured.");
            }

            if(vote.getType().equals(Vote.UPVOTE))
                comment.setUpVotes(comment.getUpVotes() - 1);
            else
                comment.setDownVotes(comment.getDownVotes() - 1);

        }
        else if (optVote.isPresent() && !optVote.get().getType().equals(vote.getType())){

            if(!voteService.deleteById(optVote.get().getId())) {
                result = new ResultObject("E16", "error", "An error occured.");
            }

            if(vote.getType().equals(Vote.UPVOTE)) {
                comment.setUpVotes(comment.getUpVotes() + 2);
                vote = new Vote();
                vote.setType(Vote.UPVOTE);
            }
            else {
                comment.setDownVotes(comment.getDownVotes() + 2);
                vote = new Vote();
                vote.setType(Vote.DOWNVOTE);
            }

            vote.setComment(comment);
            vote.setUser(user);

            if(!voteService.save(vote)) {
                result = new ResultObject("E17", "error", "An error occured.");
            }
        }
        else {
            if(Vote.UPVOTE.equals(vote.getType())) {
                comment.setUpVotes(comment.getUpVotes() + 1);
                vote = new Vote();
                vote.setType(Vote.UPVOTE);
            } else if(Vote.DOWNVOTE.equals(vote.getType())){
                comment.setDownVotes(comment.getDownVotes() + 1);

                vote = new Vote();
                vote.setType(Vote.DOWNVOTE);
            }

            vote.setComment(comment);
            vote.setUser(user);

            if(!voteService.save(vote)) {
                result = new ResultObject("E18", "error", "An error occured.");
            }
        }

        if (!commentService.save(comment)) {
            result = new ResultObject("E19", "error", "An error occured.");
        }

        model.addAttribute("post", postService.findById(postId));
        if(result != null) {
            redirectAttributes.addFlashAttribute("commentResult", result);
        }

        return "redirect:/view_post?id="+postId;
    }*/

}

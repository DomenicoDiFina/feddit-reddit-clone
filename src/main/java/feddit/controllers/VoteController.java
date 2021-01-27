package feddit.controllers;

import feddit.model.Post;
import feddit.model.User;
import feddit.model.Vote;
import feddit.security.FedditUserDetails;
import feddit.services.PostService;
import feddit.services.UserService;
import feddit.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/votePost/{id}")
    public ModelAndView processUpVote(@AuthenticationPrincipal FedditUserDetails userDetails,
                                       ModelAndView mav,
                                       RedirectAttributes redirectAttributes,
                                      @PathVariable long id, Vote vote) throws Exception {
        User user = userService.findByUsername(userDetails.getUsername());
        Post post = postService.findById(id);
        Optional<Vote> optVote = voteService.findByPostAndUser(user, post);

        if (optVote.isPresent() &&
                optVote.get().getType()
                        .equals(vote.getType())) {
            redirectAttributes.addFlashAttribute("voteError", "Vote already added");
        }
        else {
            vote.setPost(post);
            vote.setUser(user);
            System.out.println("Il voto è: " + vote.getType());
            if("UPVOTE".equals(vote.getType())) {
                post.setUpVotes(post.getUpVotes() + 1);
            } else if("DOWNVOTE".equals(vote.getType())){
                post.setDownVotes(post.getDownVotes() + 1);
            }
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
        mav.setViewName("redirect:/");
        return mav;
    }

}

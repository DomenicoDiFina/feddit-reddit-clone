package feddit.controllers;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.User;
import feddit.security.FedditUserDetails;
import feddit.services.CommentService;
import feddit.services.PostService;
import feddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    /*@GetMapping("/index")
    public String showAddPostForm(Model model) {

        model.addAttribute("post", new Post());
        return "index";
    }*/

    @PostMapping("/addpost")
    public ModelAndView processAddPost(@AuthenticationPrincipal FedditUserDetails userDetails,
                                       ModelAndView mav,
                                       RedirectAttributes redirectAttributes,
                                       Post post) {

        post.setUser(userService.findByUsername(userDetails.getUsername()));
        if(postService.save(post)) {
            redirectAttributes.addFlashAttribute("postAdded", "Post added successfully");
        } else {
            redirectAttributes.addFlashAttribute("postError", "An error occured.");
        }
        mav.setViewName("redirect:/");
        return mav;
    }

    @RequestMapping(value="/removePost/{id}", method = RequestMethod.DELETE)
    public ModelAndView removePost(ModelAndView mav, RedirectAttributes redirectAttributes,
                                   @PathVariable long id){
        postService.remove(id);
        redirectAttributes.addFlashAttribute("postRemoved", "Post removed successfully");
        mav.setViewName("redirect:/");
        return mav;
    }

    @PostMapping("/view_post/{id}")
    public String showPost(Model model, @PathVariable long id) {
        Post post = this.postService.findById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/add_comment")
    public String addComment(Model model,
                             @RequestParam("content") String content,
                             //@RequestParam("parent") ForumObject parent,
                             @RequestParam("post") long postId,
                             @AuthenticationPrincipal FedditUserDetails userDetails) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(this.userService.findByUsername(userDetails.getUsername()));
        //parent.getComments().add(comment);
        this.commentService.save(comment);
        //System.out.println(comment.getPost());
        //System.out.println(comment.getComment());
        return "redirect:/";
    }

}

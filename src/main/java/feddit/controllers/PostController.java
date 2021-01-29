package feddit.controllers;

import feddit.model.Comment;
import feddit.model.Post;
import feddit.model.ResultObject;
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

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/add_post")
    public ModelAndView addPost(@AuthenticationPrincipal FedditUserDetails userDetails,
                                       ModelAndView mav,
                                       RedirectAttributes redirectAttributes,
                                       Post post) {

        post.setUser(userService.findByUsername(userDetails.getUsername()));
        ResultObject result;
        if(postService.save(post)) {
            result = new ResultObject("S2", "success", "Post added successfully");
        } else {
            result = new ResultObject("E4", "error", "An error occured.");
        }
        redirectAttributes.addFlashAttribute("postResult", result);
        mav.setViewName("redirect:/");
        return mav;
    }

    @RequestMapping(value="/removePost/{id}", method = RequestMethod.DELETE)
    public ModelAndView deletePost(ModelAndView mav, RedirectAttributes redirectAttributes,
                                   @PathVariable long id){
        ResultObject result;

        if (postService.remove(id)) {
            result = new ResultObject("S3", "success", "Post removed successfully");
        } else {
            result = new ResultObject("E9", "error", "An error occured.");
        }
        redirectAttributes.addFlashAttribute("postResult", result);
        mav.setViewName("redirect:/");
        return mav;
    }

    @GetMapping("/view_post")
    public String showPost(Model model,
                           @RequestParam("id") long id,
                           @ModelAttribute("commentResult") ResultObject commentResult)
    {
        Post post = this.postService.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("commentResult", commentResult);
        return "post";
    }

    @PostMapping("/add_comment")
    public String addComment(Model model,
                             @RequestParam("content") String content,
                             @RequestParam("parent_type") String parentType,
                             @RequestParam("parent_id") long parentId,
                             @RequestParam("post") long postId,
                             @AuthenticationPrincipal FedditUserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(this.userService.findByUsername(userDetails.getUsername()));
        ResultObject result;
        if (parentType.equals("Comment")) {
            Comment parent = this.commentService.findById(parentId);
            comment.setComment(parent);
        } else if (parentType.equals("Post")) {
            Post parent = this.postService.findById(parentId);
            comment.setPost(parent);
        }
        if(commentService.save(comment)) {
            result = new ResultObject("S4", "success", "Comment added successfully");
        } else {
            result = new ResultObject("E5", "error", "An error occured.");
        }

        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        redirectAttributes.addFlashAttribute("commentResult", result);
        return "redirect:/view_post?id="+postId;
    }

    @PostMapping("/delete_comment")
    public String deleteComment(Model model,
                                @RequestParam("id") long commentId,
                                @RequestParam("post") long postId,
                                RedirectAttributes redirectAttributes) {
    ResultObject result;
        if (this.commentService.deleteById(commentId)) {
            result = new ResultObject("S7", "success", "Comment removed successfully");

        } else {
            result = new ResultObject("E10", "error", "An error occured.");
        }
        model.addAttribute("post", postService.findById(postId));
        redirectAttributes.addFlashAttribute("commentResult", result);
        return "redirect:/view_post?id="+postId;
    }

}

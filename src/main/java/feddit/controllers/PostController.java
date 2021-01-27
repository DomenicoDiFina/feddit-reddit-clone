package feddit.controllers;

import feddit.model.Post;
import feddit.model.User;
import feddit.security.FedditUserDetails;
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
        postService.save(post);
        redirectAttributes.addFlashAttribute("postAdded", "Post added successfully");
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

}

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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

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

    @PostMapping("/process_add-post")
    public String processAddPost(@AuthenticationPrincipal FedditUserDetails userDetails, Post post, Model model) {
        String username = userDetails.getUsername();

        User user = userService.findByUsername(username);
        post.setUser(userService.findByUsername(userDetails.getUsername()));

        postService.save(post);

        return "add-post_success";
    }
}

package feddit.controllers;

import feddit.model.Post;
import feddit.model.User;
import feddit.repositories.PostRepository;
import feddit.repositories.UserRepository;
import feddit.security.FedditUserDetails;
import feddit.services.PostService;
import feddit.services.UserAuthService;
import feddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;
import java.util.ArrayList;
import java.util.Set;

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
    public String processAddPost(@AuthenticationPrincipal FedditUserDetails userDetails,Post post, Model model) {
        String username = userDetails.getUsername();

        User user = userService.findUserByUsername(username);
        System.out.println(user.getUsername() + " " + user.getName());
        post.setUser(userService.findUserByUsername(userDetails.getUsername()));
        postService.savePost(post);

        return "add-post_success";
    }
}

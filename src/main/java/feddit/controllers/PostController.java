package feddit.controllers;

import feddit.model.Post;
import feddit.model.User;
import feddit.repositories.PostRepository;
import feddit.repositories.UserRepository;
import feddit.security.FedditUserDetails;
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
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepo;

    /*@GetMapping("/index")
    public String showAddPostForm(Model model) {

        model.addAttribute("post", new Post());
        return "index";
    }*/

    @PostMapping("/process_add-post")
    public String processAddPost(@AuthenticationPrincipal FedditUserDetails userDetails,Post post, Model model) {
        post.setUser(userRepo.findByUsername(userDetails.getUsername()));
        postRepository.save(post);

        return "add-post_success";
    }
}

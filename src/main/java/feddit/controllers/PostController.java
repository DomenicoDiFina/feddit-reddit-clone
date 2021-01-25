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

import java.awt.*;
import java.util.Set;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/add-post")
    public String showAddPostForm(@AuthenticationPrincipal FedditUserDetails userDetails, Model model) {
        System.out.println("Ciao");
        User user = userRepo.findByUsername(userDetails.getUsername());
        model.addAttribute("post", new Post(user));
        return "add-post";
    }

    @PostMapping("/process_add-post")
    public String processAddPost(Post post, Model model) {
        System.out.println(post.getPostID());
        System.out.println(post.getTitle());
        System.out.println(post.getDescription());
        System.out.println(post.getCreationDate());
        System.out.println(post.getComments());
        System.out.println(post.getUpvotes());
        System.out.println(post.getDownvotes());
        postRepository.save(post);
        System.out.println(post.getTitle());

        /*
        if(userRepo.findByUsername(user.getUsername()) == null) {
            userRepo.save(user);
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("usernameError", "Username already exists");
            return showSignUpForm(model);
        }
        */
        return "add-post_success";
    }
}

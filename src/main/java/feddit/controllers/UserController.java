package feddit.controllers;

//import feddit.model.ChangePasswordObj;
import feddit.model.Post;
import feddit.model.Role;
import feddit.model.User;
import feddit.repositories.PostRepository;
import feddit.repositories.RoleRepository;
import feddit.repositories.UserRepository;
import feddit.security.FedditUserDetails;
import feddit.services.RoleService;
import feddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    //private UserRepository userRepo;
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PostRepository postRepository;


    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("post", new Post());
        List<Post> posts = (List<Post>) postRepository.findAll();
        Collections.sort(posts, new Comparator<Post>() {
            public int compare(Post p1, Post p2) {
                return Integer.compare((p1.getUpvotes() - p1.getDownvotes()), p2.getUpvotes() - p2.getDownvotes());
            }
        });
        model.addAttribute("posts", posts);
        return "index";
    }

    /*@GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("post", new Post());
        return "index";
    }*/

    /*@GetMapping("/changepassword")
    public String showChangePasswordForm(Model model) {
        //model.addAttribute("password", new ChangePasswordObj());
        return "changepassword";
    }*/

    @RequestMapping(value = "/process_changepassword", method = RequestMethod.POST)
    public ModelAndView processChangePassword(ModelAndView mav,
                                        RedirectAttributes redirectAttributes,
                                        @RequestParam("old_password") String oldPassword,
                                        @RequestParam("new_password") String newPassword) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User u = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!oldPassword.equals(newPassword)){
            if(passwordEncoder.matches(oldPassword, u.getPassword())){

                u.setPassword(passwordEncoder.encode(newPassword));
                userService.save(u);
                redirectAttributes.addFlashAttribute("passwordResult", "1");
            } else {
                redirectAttributes.addFlashAttribute("passwordResult", "-1");
            }
        } else {
            redirectAttributes.addFlashAttribute("passwordResult", "-2");
        }
        mav.setViewName("redirect:/myaccount");
        return mav;
    }

    @GetMapping("/sign-up")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/process_signup")
    public String processSignUp(User user, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Set.of(roleService.findByName("USER")));
        if(userService.findByUsername(user.getUsername()) == null) {
            userService.save(user);
            model.addAttribute("name", user.getName());
        } else {
            model.addAttribute("usernameError", "Username already exists");
            return showSignUpForm(model);
        }
        return "sign-up_success";
    }

    @RequestMapping("login")
    public String showLogin() {
        return "login";
    }


    @RequestMapping("login_error")
    public String showLoginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    /*@RequestMapping("/admin")
    public String showLoginAdmin() {
        return "admin";
    }

    @RequestMapping("login_admin_error")
    public String showAdminLoginError(Model model) {
        model.addAttribute("loginError", true);
        return "admin";
    }*/

    @GetMapping("/myaccount")
    public String showMyAccount(Model model,
                                @AuthenticationPrincipal FedditUserDetails userDetails,
                                @ModelAttribute("passwordResult") String passwordResult) {

        try {
            int errorCode = Integer.parseInt(passwordResult);
            switch (errorCode) {
                case 1:
                    model.addAttribute("passwordChanged", "Password changed successfully.");
                    break;
                case -1:
                    model.addAttribute("passwordError", "Old password isn't correct.");
                    break;
                case -2:
                    model.addAttribute("passwordError", "New password is equal to the old inserted.");
                    break;
            }
        } catch (NumberFormatException ex) {

        }

        model.addAttribute("name", userDetails.getName());
        model.addAttribute("surname", userDetails.getSurname());
        model.addAttribute("email", userDetails.getEmail());
        model.addAttribute("birth", userDetails.getBirth());
        User user = userService.findByUsername(userDetails.getUsername());

        List<Post> posts = postRepository.findAllByUser(user);
        Collections.sort(posts, new Comparator<Post>() {
            public int compare(Post p1, Post p2) {
                return p2.getCreationDate().compareTo(p1.getCreationDate());
            }
        });
        model.addAttribute("posts", posts);

        return "myaccount";
    }
}

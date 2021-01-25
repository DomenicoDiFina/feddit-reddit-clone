package feddit.controllers;

//import feddit.model.ChangePasswordObj;
import feddit.model.Post;
import feddit.model.Role;
import feddit.model.User;
import feddit.repositories.PostRepository;
import feddit.repositories.RoleRepository;
import feddit.repositories.UserRepository;
import feddit.security.FedditUserDetails;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PostRepository postRepository;


    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("post", new Post());
        return "index";
    }

    /*@GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("post", new Post());
        return "index";
    }*/

    @GetMapping("/changepassword")
    public String showChangePasswordForm(Model model) {
        //model.addAttribute("password", new ChangePasswordObj());
        return "changepassword";
    }

    @RequestMapping(value = "/process_changepassword", method = RequestMethod.POST)
    public String processChangePassword(Authentication auth,
                                        Model model,
                                        @RequestParam("old_password") String oldPassword,
                                        @RequestParam("new_password") String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        FedditUserDetails userDetails = (FedditUserDetails) auth.getPrincipal();

        if(!oldPassword.equals(newPassword)){
            if(passwordEncoder.matches(oldPassword, userDetails.getPassword())){
                userRepo.changeUserPassword(userDetails.getUsername(), passwordEncoder.encode(newPassword));
                model.addAttribute("passwordChanged", "Password changed successfully");
                return showMyAccount(userDetails, model);
            } else {
                System.out.println("Error: old password incorrect");
                model.addAttribute("passwordError", "Old password isn't correct");
            }
        } else {
            System.out.println("Error: same passowrd");
            model.addAttribute("passwordError", "The new password is equal to the old one");
        }
        return "changepassword";
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
        user.setRoles(Set.of(roleRepo.findByName("USER")));
        if(userRepo.findByUsername(user.getUsername()) == null) {
            userRepo.save(user);
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
    public String showMyAccount(@AuthenticationPrincipal FedditUserDetails userDetails, Model model) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) auth.getDetails();
        model.addAttribute("name", userDetails.getName());
        model.addAttribute("surname", userDetails.getSurname());
        model.addAttribute("email", userDetails.getEmail());
        model.addAttribute("birth", userDetails.getBirth());
        User user = userRepo.findByUsername(userDetails.getUsername());

        List<Post> posts = postRepository.findAllByUser(user);
        model.addAttribute("posts", posts);


        return "myaccount";
    }

    @PostMapping("/myaccount")
    public String getAllPosts(@AuthenticationPrincipal FedditUserDetails userDetails, Model model){
        User user = userRepo.findByUsername(userDetails.getUsername());

        return "my_account";
    }




}

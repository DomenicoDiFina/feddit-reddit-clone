package feddit.controllers;

//import feddit.model.ChangePasswordObj;
import feddit.model.ResultObject;
import feddit.model.Post;
import feddit.model.User;
import feddit.security.FedditUserDetails;
import feddit.services.PostService;
import feddit.services.RoleService;
import feddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PostService postService;


    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("post", new Post());

        List<Post> posts = this.postService.findAll();
        Collections.sort(posts, Comparator.comparingInt(p -> p.getDownVotes() - p.getUpVotes()));

        model.addAttribute("posts", posts);

        return "index";
    }

    @RequestMapping(value = "/process_changepassword", method = RequestMethod.POST)
    public ModelAndView processChangePassword(ModelAndView mav,
                                        RedirectAttributes redirectAttributes,
                                        @RequestParam("old_password") String oldPassword,
                                        @RequestParam("new_password") String newPassword) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User u = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ResultObject result;
        if(!oldPassword.equals(newPassword)){
            if(passwordEncoder.matches(oldPassword, u.getPassword())){

                u.setPassword(passwordEncoder.encode(newPassword));
                if(userService.save(u)) {
                    result = new ResultObject("S1", "success", "Password changed successfully.");
                } else {
                    result = new ResultObject("E1", "error", "An error occured.");
                }
            } else {
                result = new ResultObject("E2", "error", "Old password isn't correct.");
            }
        } else {
            result = new ResultObject("E3", "error", "New password is equal to the old inserted.");
        }
        redirectAttributes.addFlashAttribute("passwordResult", result);
        mav.setViewName("redirect:/myaccount");
        return mav;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model,
                                 @ModelAttribute("usernameError") String usernameError) {
        model.addAttribute("usernameError", usernameError);
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/process_signup")
    public ModelAndView processSignUp(User user,
                                      ModelAndView mav,
                                      RedirectAttributes redirectAttributes) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Set.of(roleService.findByName("USER")));

        if(userService.findByUsername(user.getUsername()) == null) {
            if(userService.save(user)) {
                redirectAttributes.addFlashAttribute("name", user.getFirstName());
                mav.setViewName("redirect:/signup_success");
            } else {
                redirectAttributes.addFlashAttribute("usernameError", "An error occured");
                mav.setViewName("redirect:/signup");
            }
        } else {
            redirectAttributes.addFlashAttribute("usernameError", "Username already exists");
            mav.setViewName("redirect:/signup");
        }

        return mav;
    }

    @GetMapping("/signup_success")
    public String showSignUpSuccess(Model model,
                                    @ModelAttribute("name") String name) {
        model.addAttribute("name", name);
        return "signup_success";
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

    @GetMapping("/myaccount")
    public String showMyAccount(Model model,
                                @AuthenticationPrincipal FedditUserDetails userDetails,
                                @ModelAttribute("passwordResult") ResultObject passwordResult) {

        model.addAttribute("passwordResult", passwordResult);
        model.addAttribute("first_name", userDetails.getFirstName());
        model.addAttribute("last_name", userDetails.getLastName());
        model.addAttribute("email", userDetails.getEmail());
        model.addAttribute("birth_date", userDetails.getBirthDate());
        User user = userService.findByUsername(userDetails.getUsername());

        List<Post> posts = user.getPosts();
        Collections.sort(posts, (p1, p2) ->  p2.getCreationDate().compareTo(p1.getCreationDate()));
        model.addAttribute("posts", posts);

        return "myaccount";
    }

}

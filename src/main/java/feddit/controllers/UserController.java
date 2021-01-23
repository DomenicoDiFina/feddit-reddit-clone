package feddit.controllers;

//import feddit.model.ChangePasswordObj;
import feddit.model.User;
import feddit.repositories.UserRepository;
import feddit.security.FedditUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

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
            System.out.println("old e new sono diverse "+oldPassword+", "+newPassword);
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
    public String processSignUp(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "index";
    }

    @RequestMapping("/login_error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/myaccount")
    public String showMyAccount(@AuthenticationPrincipal FedditUserDetails userDetails, Model model) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) auth.getDetails();
        model.addAttribute("name", userDetails.getName());
        model.addAttribute("surname", userDetails.getSurname());
        model.addAttribute("email", userDetails.getEmail());
        model.addAttribute("birth", userDetails.getBirth());


        return "myaccount";
    }


}

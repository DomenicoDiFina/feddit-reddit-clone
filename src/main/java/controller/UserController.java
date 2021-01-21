package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import test.Post;

@Controller
public class UserController {

    @GetMapping("/sign-up")
    public String getSignupPage(Model model) {
        return "sign-up";
    }
}

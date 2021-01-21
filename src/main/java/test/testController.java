package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class testController {

    @Autowired
    private PostService service;


    @GetMapping("/mypost")
    public String getMyArticle(Model model) {
        Post post = new Post("Test title", "Test content");
        this.service.save(post);
        model.addAttribute("post1", post);
        return "post";
    }

}

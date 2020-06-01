package microblog.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsController {

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }
}

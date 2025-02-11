package webProject.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("")
    public String index() {
        return "/index.html";
    }

    @GetMapping("/member/signup")
    public String signup(){return "/member/signup.html";}
    @GetMapping("/member/login")
    public String login(){return "/member/login.html";}
    @GetMapping("/request/post")
    public String requestPost() {
        return "/request/post.html";
    }
}

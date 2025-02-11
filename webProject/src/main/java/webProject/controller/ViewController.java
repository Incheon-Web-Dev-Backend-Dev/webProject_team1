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

    @GetMapping("/request/post")
    public String requestPost() {
        return "/request/post.html";
    }
    // 견적서 쓰기 (업체 / 개인수납가)
    @GetMapping("/estimate/write")
    public String estimateWrite(){
        return "/estimate/write.html";
    }
}

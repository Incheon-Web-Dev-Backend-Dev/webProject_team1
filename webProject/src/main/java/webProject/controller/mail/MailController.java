package webProject.controller.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import webProject.model.dto.mail.MailDto;
import webProject.service.mail.MailService;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/contact/send")
    public String mail(@RequestBody MailDto mailDto){
       return "1";
    }
}

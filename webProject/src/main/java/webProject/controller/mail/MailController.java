package webProject.controller.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webProject.service.mail.MailService;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    // 1. 리뷰 링크를 생성하는 QR코드를 이메일로 발송
    @PostMapping("/mail/sendQRCode")
    public ResponseEntity<?> sendMailQrcode(@RequestParam int estno){
        boolean result = mailService.sendMailQrcode(estno);
        if(result) {
            return ResponseEntity.ok().body("sendMailQrcode ok");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sendMailQrcod error");
        } // if-else end
    }// sendMailQRcode

}

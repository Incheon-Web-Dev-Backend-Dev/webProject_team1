package webProject.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import webProject.model.dto.member.MemberDto;
import webProject.service.manage.ManageService;

import java.util.List;

@RestController
public class ManageController {
    @Autowired private ManageService manageService;

    @GetMapping("/manage/memberlist.do")
    public List<MemberDto> memberList(){
        return manageService.memberList();
    }

}

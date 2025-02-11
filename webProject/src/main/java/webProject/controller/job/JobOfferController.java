package webProject.controller.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.job.JobOfferDto;
import webProject.service.job.JobOfferService;

import java.util.List;

@RestController
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    // 구인글 작성
    @PostMapping("/joboffer/write.do")
    public boolean jobOfferWrite(JobOfferDto jobOfferDto){return jobOfferService.jobOfferWrite(jobOfferDto);}

    // 구인글 전체 조회
    @GetMapping("/joboffer/findall.do")
    public List<JobOfferDto> jobOfferFindAll(){return jobOfferService.jobOfferFindAll();}

    // 구인글 개별 조회
    @GetMapping("/joboffer/find.do")
    public JobOfferDto jobOfferFind(@RequestParam int jono){return jobOfferService.jobOfferFind(jono);}

    // 구인글 내용 수정
    @PutMapping("/joboffer/update.do")
    public boolean jobOfferUpdate(JobOfferDto jobOfferDto){return jobOfferService.jobOfferUpdate(jobOfferDto);}

    // 구인글 마감상태 수정
    @PutMapping("/joboffer/stateupdate.do")
    public boolean jobStateUpdate(@RequestParam int jono){return jobOfferService.jobStateUpdate(jono);}

    // 구인글 삭제
    @DeleteMapping("/joboffer/delete.do")
    public boolean jobOfferDelete(@RequestParam int jono){return jobOfferService.jobOfferDelete(jono);}

    // 내가 쓴 구인글 조회
    @GetMapping("/joboffer/mylist.do")
    public List<JobOfferDto> jobOfferMyList(@RequestParam int mno){return jobOfferService.jobOfferMyList(mno);}
}

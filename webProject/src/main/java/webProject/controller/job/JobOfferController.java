package webProject.controller.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.dto.job.JobPageDto;
import webProject.service.job.JobOfferService;

import java.util.List;

@RestController
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    // 구인글 작성
    @PostMapping("/joboffer/write.do")
    public boolean jobOfferWrite(@RequestBody JobOfferDto jobOfferDto){return jobOfferService.jobOfferWrite(jobOfferDto);}

    // 구인글 전체 조회 - 지도 표시용
    @GetMapping("/joboffer/map.do")
    public List<JobOfferDto> jobOfferMap(@RequestParam String joservice){return jobOfferService.jobOfferMap(joservice);}

    // 구인글 전체 조회 + 페이징/검색
    @GetMapping("/joboffer/findall.do")
    public JobPageDto jobOfferFindSearch(@RequestParam int page, @RequestParam String key, @RequestParam String keyword) {
        return jobOfferService.jobOfferFindSearch(page, key, keyword);}

    // 구인글 개별 조회
    @GetMapping("/joboffer/find.do")
    public JobOfferDto jobOfferFind(@RequestParam int jono){return jobOfferService.jobOfferFind(jono);}

    // 구인글 내용 수정
    @PutMapping("/joboffer/update.do")
    public boolean jobOfferUpdate(@RequestBody JobOfferDto jobOfferDto){return jobOfferService.jobOfferUpdate(jobOfferDto);}

    // 구인글 마감상태 수정
    @PutMapping("/joboffer/stateupdate.do")
    public boolean jobStateUpdate(@RequestParam int jono){return jobOfferService.jobStateUpdate(jono);}

    // 구인글 삭제
    @DeleteMapping("/joboffer/delete.do")
    public boolean jobOfferDelete(@RequestParam int jono){return jobOfferService.   jobOfferDelete(jono);}

    // 내가 쓴 구인글 조회
    @GetMapping("/joboffer/mylist.do")
//    public List<JobOfferDto> jobOfferMyList(){return jobOfferService.jobOfferMyList();}
    public JobPageDto jobofferMyList(@RequestParam int page, @RequestParam String key, @RequestParam String keyword){
        return jobOfferService.jobOfferMyList(page,key,keyword);}
}

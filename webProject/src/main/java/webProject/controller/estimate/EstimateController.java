package webProject.controller.estimate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.estimate.EstimateDto;
import webProject.model.dto.member.MemberDto;
import webProject.service.estimate.EstimateService;

import java.util.List;
import java.util.Optional;

@RestController
public class EstimateController {
    @Autowired EstimateService estimateService;

    // 견적글 쓰기
    @PostMapping("/estimate/write.do")
    public boolean estimateWrite(@RequestBody EstimateDto estimateDto){
         return estimateService.estimateWrite(estimateDto);
    }
    // 견적서 전체 보기
    @GetMapping("/estimate/findall.do")
    public List<EstimateDto> estimateFindAll(@RequestParam int reqno){
        return estimateService.estimateFindAll(reqno);
    }
    // 견적글 선택 보기
    @GetMapping("/estimate/find.do")
    public EstimateDto estimateFind(@RequestParam int estno) {
        return estimateService.estimateFind(estno);
    }

    @GetMapping("/estimate/mywrote.do")
    public List<EstimateDto> estimateMyWrote(){
        return estimateService.estimateMyWrote();
    }

    @DeleteMapping("/estimeate/delete")
    public boolean estimateDelete (@RequestParam int estno) {
        return estimateService.estimateDelete(estno);
    }
}

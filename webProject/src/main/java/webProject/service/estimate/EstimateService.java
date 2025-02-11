package webProject.service.estimate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.estimate.EstimateDto;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.request.RequestEntity;
import webProject.model.repository.estimate.EstimateRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {
    @Autowired EstimateRepository estimateRepository;
    @Autowired RequestEntity requestEntity;

    // 견적글 쓰기
    public boolean estimateWrite(EstimateDto estimateDto){
        return false;
    }
    // 견적글 전체 보기
    public List<EstimateDto> estimateFindAll(int rno){
        List<EstimateEntity> estimateEntityList = estimateRepository.findAll();
        List<EstimateDto> estimateDtoList =new ArrayList<>();
        estimateEntityList.forEach(estimateEntity -> {
            if(estimateEntity.getRequestEntity().getReqno() == rno ) {
                EstimateDto estimateDto = estimateEntity.toESDto();
            } else {

            }
        });
        return estimateDtoList;
    }
    // 견적글 개별 조회
    public EstimateDto estimateFind(int eno){
        return null;
    }
    // 현재 로그인된 회원이 작성한 견적글 개별 조회
    public List<EstimateDto> estimateMyFind(int eno){
        return null;
    }
}

package webProject.service.estimate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.estimate.EstimateDto;
import webProject.model.repository.estimate.EstimateRepository;

import java.util.List;

@Service
public class EstimateService {
    @Autowired EstimateRepository estimateRepository;

    public boolean estimateWrite(EstimateDto estimateDto){
        return false;
    }
    public List<EstimateDto> estimateFindAll(){
        return null;
    }
    public EstimateDto estimateFind(int eno){
        return null;
    }
}

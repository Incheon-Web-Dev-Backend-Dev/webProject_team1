package webProject.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.job.JobOfferEntity;
import webProject.model.repository.job.JobOfferRepository;
import webProject.model.repository.member.MemberRepository;

import java.util.List;

@Service
public class JobOfferService {

    @Autowired
    private JobFileService jobFileService;
    @Autowired
    private JobOfferRepository jobOfferRepository;
    @Autowired
    private MemberRepository memberRepository;

    public boolean jobOfferWrite(JobOfferDto jobOfferDto) {

        JobOfferEntity jobOfferEntity = jobOfferDto.toEntity();
        return false;
    }

    public List<JobOfferDto> jobOfferFindAll() {
        return null;
    }

    public JobOfferDto jobOfferFind(int jono) {
        return null;
    }

    public boolean jobOfferUpdate(JobOfferDto jobOfferDto) {
        return false;
    }

    public boolean jobStateUpdate(int jono) {
        return false;
    }

    public boolean jobOfferDelete(int jono) {
        return false;
    }

    public List<JobOfferDto> jobOfferMyList(int mno) {
        return null;
    }
}

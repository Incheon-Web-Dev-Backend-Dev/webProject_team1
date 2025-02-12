package webProject.service.job;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webProject.model.dto.job.JobFileDto;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.job.JobFileEntity;
import webProject.model.entity.job.JobOfferEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.repository.job.JobFileRepository;
import webProject.model.repository.job.JobOfferRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.service.member.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobOfferService {

    @Autowired
    private JobFileService jobFileService;
    @Autowired
    private JobOfferRepository jobOfferRepository;
    @Autowired
    private JobFileRepository jobFileRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Transactional
    public boolean jobOfferWrite(JobOfferDto jobOfferDto) {
//        MemberDto loginDto = memberService.getMyInfo();
//        if (loginDto == null){return false;}
//        MemberEntity loginEntity = memberRepository.findById(loginDto.getMno()).get();

        JobOfferEntity jobOfferEntity = jobOfferDto.toEntity();
//        jobOfferEntity.setMemberEntity(loginEntity);
        JobOfferEntity saveEntity = jobOfferRepository.save(jobOfferEntity);

        List<MultipartFile> uploadFiles = jobOfferDto.getUploadFiles();
        JobFileDto jobFileDto = new JobFileDto();
        if (uploadFiles!=null){
            try {
                for (int index = 0; index <= uploadFiles.size()-1; index++) {
                    String fileName = jobFileService.fileUpload(uploadFiles.get(index));
                    jobFileDto.setJfname(fileName);
                    System.out.println(jobFileDto);
                    JobFileEntity jobFileEntity = jobFileDto.toEntity();
                    jobFileEntity.setJobOfferEntity(jobOfferEntity);
                    JobFileEntity saveFileEntity = jobFileRepository.save(jobFileEntity);
                    if (!(saveFileEntity.getJfno() > 0)){return false;}
                }
            } catch (Exception e){System.out.println(e); return false;}
        }

        if (saveEntity.getJono() > 0){return true;}
        return false;
    }

    public List<JobOfferDto> jobOfferFindAll() {
        List<JobOfferEntity> jobOfferEntityList = jobOfferRepository.findAll();
        List<JobOfferDto> list = new ArrayList<>();

        jobOfferEntityList.forEach(jobOfferEntity -> {
            JobOfferDto jobOfferDto = jobOfferEntity.toDto();
            list.add(jobOfferDto);
        });
        return list;
    }

    public JobOfferDto jobOfferFind(int jono) {
        Optional<JobOfferEntity> optionalList = jobOfferRepository.findById(jono);
        if (optionalList.isPresent()){
            JobOfferEntity jobOfferEntity = optionalList.get();
            JobOfferDto jobOfferDto = jobOfferEntity.toDto();

            List<JobFileEntity> jobFileEntityList = jobFileRepository.findAll();
            List<JobFileDto> jobFileDtoList = new ArrayList<>();

        }
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

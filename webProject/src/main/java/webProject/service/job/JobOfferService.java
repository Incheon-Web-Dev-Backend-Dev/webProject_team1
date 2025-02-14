package webProject.service.job;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.job.JobOfferEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.repository.job.JobFileRepository;
import webProject.model.repository.job.JobOfferRepository;
import webProject.model.repository.like.LikeRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.service.member.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobOfferService {

    @Autowired
    private JobFileService jobFileService;
    @Autowired
    private JobOfferRepository jobOfferRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LikeRepository likeRepository;

    @Transactional
    public boolean jobOfferWrite(JobOfferDto jobOfferDto) {
        MemberDto loginDto = memberService.getMyInfo();
        if (loginDto == null){return false;}
        MemberEntity loginEntity = memberRepository.findById(loginDto.getMno()).get();

        JobOfferEntity jobOfferEntity = jobOfferDto.toEntity();
        jobOfferEntity.setMemberEntity(loginEntity);
        JobOfferEntity saveEntity = jobOfferRepository.save(jobOfferEntity);

//        List<MultipartFile> uploadFiles = jobOfferDto.getUploadFiles();
//        JobFileDto jobFileDto = new JobFileDto();
//        if (uploadFiles!=null){
//            try {
//                for (int index = 0; index <= uploadFiles.size()-1; index++) {
//                    String fileName = jobFileService.fileUpload(uploadFiles.get(index));
//                    jobFileDto.setJfname(fileName);
//                    JobFileEntity jobFileEntity = jobFileDto.toEntity();
//                    jobFileEntity.setJobOfferEntity(jobOfferEntity);
//                    JobFileEntity saveFileEntity = jobFileRepository.save(jobFileEntity);
//                    if (!(saveFileEntity.getJfno() > 0)){return false;}
//                }
//            } catch (Exception e){System.out.println(e); return false;}
//        }

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
            return jobOfferDto;
        }
        return null;
    }

    @Transactional
    public boolean jobOfferUpdate(JobOfferDto jobOfferDto) {
        JobOfferEntity updateEntity = jobOfferRepository.findById(jobOfferDto.getJono()).get();
        updateEntity.setJotitle(jobOfferDto.getJotitle());
        updateEntity.setJocontent(jobOfferDto.getJocontent());
        updateEntity.setJoservice(jobOfferDto.getJoservice());
        updateEntity.setJocity(jobOfferDto.getJocity());
        updateEntity.setJodistrict(jobOfferDto.getJodistrict());
        return true;
    }

    @Transactional
    public boolean jobStateUpdate(int jono) {
        JobOfferEntity entity = jobOfferRepository.findById(jono).get();
        entity.setJostate(!entity.isJostate());
        return true;
    }

    @Transactional
    public boolean jobOfferDelete(int jono) {
        likeRepository.deleteByJono(jono);
        jobOfferRepository.deleteById(jono);
        return true;
    }

    public List<JobOfferDto> jobOfferMyList() {
        String mid = memberService.getSession();
        MemberEntity loginEntity = memberRepository.findByMemail(mid);
        List<JobOfferEntity> jobOfferEntityList = jobOfferRepository.findByMemberEntity_Mno(loginEntity.getMno());
        List<JobOfferDto> list = new ArrayList<>();
        jobOfferEntityList.forEach(jobOfferEntity -> {
            JobOfferDto jobOfferDto = jobOfferEntity.toDto();
            list.add(jobOfferDto);
        });
        return list;
    }
}

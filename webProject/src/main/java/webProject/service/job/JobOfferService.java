package webProject.service.job;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.dto.job.JobPageDto;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.job.JobOfferEntity;
import webProject.model.entity.like.LikeEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.repository.job.JobOfferRepository;
import webProject.model.repository.like.LikeRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.service.member.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobOfferService {

    @Autowired private JobOfferRepository jobOfferRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;
    @Autowired private LikeRepository likeRepository;


    // 구인글 작성
    @Transactional
    public boolean jobOfferWrite(JobOfferDto jobOfferDto) {

        MemberDto loginDto = memberService.getMyInfo();
        if (loginDto == null){return false;}
        MemberEntity loginEntity = memberRepository.findById(loginDto.getMno()).get();

        JobOfferEntity jobOfferEntity = jobOfferDto.toEntity();
        jobOfferEntity.setMemberEntity(loginEntity);
        JobOfferEntity saveEntity = jobOfferRepository.save(jobOfferEntity);

        if (saveEntity.getJono() > 0){return true;}
        return false;
    }

//    // 구인글 전체 조회
//    public List<JobOfferDto> jobOfferFindAll() {
//
//        List<JobOfferEntity> jobOfferEntityList = jobOfferRepository.findAll();
//        List<JobOfferDto> list = new ArrayList<>();
//
//        jobOfferEntityList.forEach(jobOfferEntity -> {
//            JobOfferDto jobOfferDto = jobOfferEntity.toDto();
//            list.add(jobOfferDto);
//        });
//        return list;
//    }

    // 구인글 전체 조회 검색o
    public JobPageDto jobOfferFindSearch(int page, String key, String keyword){
        Pageable pageable = PageRequest.of(page-1,3,Sort.by(Sort.Direction.DESC,"jono"));

        System.out.println("page = " + page + ", key = " + key + ", keyword = " + keyword);
        
        Page<JobOfferEntity> jobOfferEntityList = jobOfferRepository.findBySearch(key,keyword,pageable);

        System.out.println("jobOfferEntityList = " + jobOfferEntityList);
        List<JobOfferDto> list = new ArrayList<>();
        jobOfferEntityList.forEach(jobOfferEntity -> {
                JobOfferDto jobOfferDto = jobOfferEntity.toDto();
                list.add(jobOfferDto);
            });
        int totalPage = jobOfferEntityList.getTotalPages();
        long totalCount = jobOfferEntityList.getTotalElements();
        int btnSize = 3;
        int startBtn = ((page-1)/btnSize)*btnSize+1;
        int endBtn = startBtn+(btnSize-1);
        if (endBtn>=totalPage) endBtn = totalPage;
        JobPageDto pageDto = JobPageDto.builder().page(page).totalpage(totalPage).totalcount(totalCount).startbtn(startBtn).endbtn(endBtn).data(list).build();
        return pageDto;
    }

    // 구인글 개별 조회
    public JobOfferDto jobOfferFind(int jono) {
        Optional<JobOfferEntity> optionalList = jobOfferRepository.findById(jono);
        List<LikeEntity> likeEntityList = likeRepository.findLikeMaster(jono);
        if (optionalList.isPresent()){
            JobOfferEntity jobOfferEntity = optionalList.get();
            JobOfferDto jobOfferDto = jobOfferEntity.toDto();
            jobOfferDto.setLikeCount(likeEntityList.size());
            return jobOfferDto;
        }
        return null;
    }

    // 구인글 내용 수정
    @Transactional
    public boolean jobOfferUpdate(JobOfferDto jobOfferDto) {
        JobOfferEntity updateEntity = jobOfferRepository.findById(jobOfferDto.getJono()).get();
        updateEntity.setJotitle(jobOfferDto.getJotitle());
        updateEntity.setJocontent(jobOfferDto.getJocontent());
        updateEntity.setJoservice(jobOfferDto.getJoservice());
        updateEntity.setJoaddr(jobOfferDto.getJoaddr());
        updateEntity.setDetailaddr(jobOfferDto.getDetailaddr());
        updateEntity.setLongitude(jobOfferDto.getLongitude());
        updateEntity.setLatitude(jobOfferDto.getLatitude());
        return true;
    }

    // 구인글 마감상태 수정
    @Transactional
    public boolean jobStateUpdate(int jono) {
        JobOfferEntity entity = jobOfferRepository.findById(jono).get();
        entity.setJostate(!entity.isJostate());
        return true;
    }

    // 구인글 삭제
    @Transactional
    public boolean jobOfferDelete(int jono) {

        List<LikeEntity> likeEntityList = likeRepository.findAll();
        likeEntityList.forEach(likeEntity -> {
            if (likeEntity.getJobOfferEntity().getJono()==jono){
            likeRepository.delete(likeEntity);
            }
        });

//        likeRepository.deleteByQuery(jono);
        jobOfferRepository.deleteById(jono);

        return true;
    }

    // 내가 쓴 구인글 조회
    public JobPageDto jobOfferMyList(int page, String key, String keyword) {
        String mid = memberService.getSession();
        MemberEntity loginEntity = memberRepository.findByMemail(mid);

        Pageable pageable = PageRequest.of(page-1,3,Sort.by(Sort.Direction.DESC,"jono"));
        Page<JobOfferEntity> jobOfferEntityList = jobOfferRepository.findByMy(loginEntity.getMno(),key,keyword,pageable);
        List<JobOfferDto> list = new ArrayList<>();
        jobOfferEntityList.forEach(jobOfferEntity -> {
            JobOfferDto jobOfferDto = jobOfferEntity.toDto();
            list.add(jobOfferDto);
        });
        int totalPage = jobOfferEntityList.getTotalPages();
        long totalCount = jobOfferEntityList.getTotalElements();
        int btnSize = 3;
        int startBtn = ((page-1)/btnSize)*btnSize+1;
        int endBtn = startBtn+(btnSize-1);
        if (endBtn>=totalPage) endBtn = totalPage;
        JobPageDto pageDto = JobPageDto.builder().page(page).totalpage(totalPage).totalcount(totalCount).startbtn(startBtn).endbtn(endBtn).data(list).build();
        return pageDto;
    }
}

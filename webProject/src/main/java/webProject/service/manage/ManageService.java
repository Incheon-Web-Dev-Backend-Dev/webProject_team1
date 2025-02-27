package webProject.service.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.member.MemberDto;
import webProject.model.entity.member.MemberEntity;
import webProject.model.repository.manage.ManageRepository;
import webProject.model.repository.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManageService {
    @Autowired private ManageRepository manageRepository;
    @Autowired private MemberRepository memberRepository;
    // 회원 전체 출력
    public List<MemberDto> memberList() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        // MemberEntity 리스트를 MemberDto 리스트로 변환
        List<MemberDto> memberDtoList = new ArrayList<>();
        memberEntityList.forEach(member -> {
            memberDtoList.add(member.toDto()); // MemberEntity를 MemberDto로 변환하여 추가
        });
        return memberDtoList;
    }
    // 회원 정보 출력
    public MemberDto memberView(int mno){
        Optional<MemberEntity> optional = memberRepository.findById(mno);
        if(optional.isPresent()){
            MemberEntity memberEntity = optional.get();
            MemberDto memberDto = memberEntity.toDto();
            return memberDto;
        }
        return null;
    }
}

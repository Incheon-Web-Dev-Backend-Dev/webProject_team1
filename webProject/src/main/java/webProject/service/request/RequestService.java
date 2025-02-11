package webProject.service.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.request.RequestEntity;
import webProject.model.repository.request.RequestRepository;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    // 현재 로그인된 회원의 요청글 전체조회
    public List<RequestDto> requestFindAll() {
        return null;
    }

    // 현재 로그인된 회윈의 요청글 개별조회
    public RequestDto requestFind(int reqno) {
        return null;
    }

    // 견적 요청글 작성
    public boolean requestPost (RequestDto requestDto){
    // 1. 사용자로부터 전달받은 requestDto를 엔테테로 변환
        // 1. dto를 entity로 변환
        RequestEntity requestEntity = requestDto.toEntity();
        // 2. 견적서 작성자는 현재 로그인된 회원이므로 세션에서 현재 로그인된 회원번호를 조회
        //MemberDto loginDto = memberService.getMyInfo(); 아직 메서드 만들어지지 않음
        int loginTest = 1; // 테스터임 멤버에서 로그인한 메서드 만들어지면 삭제하고 위 주석코드 풀기

        // 로그인된 상태가 아니면 글쓰기 종료
        // loginTest로 무조건 로그인 되어있는걸로 가정함
        // if(login Dto == null) return false;
        if(loginTest > 1) return false;

        // 로그인된 상태이면 회원번호를 조회
        int loginMno = 2; // 테스터

        // 로그인된ㄷ 회원 엔티티를 요청서 엔티티에 대입한다.
        // MemberEntity loginEntity = MemberRepository





        return false;
    }
}

package webProject.service.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.request.RequestEntity;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestEntity requestEntity;

    // 현재 로그인된 회원의 요청글 전체조회
    public List<RequestDto> requestFindAll() {
        return null;
    }

    // 현재 로그인된 회윈의 요청글 개별조회
    public RequestDto requestFind(@RequestParam int reqno) {
        return null;
    }

    // 견적 요청글 작성
    public boolean requestPost (@RequestBody RequestDto requestDto){
        return false;
    }
}

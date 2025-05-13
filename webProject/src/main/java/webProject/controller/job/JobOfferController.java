package webProject.controller.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webProject.model.dto.job.JobInfiniteScrollDto;
import webProject.model.dto.job.JobOfferDto;
import webProject.model.dto.job.JobPageDto;
import webProject.service.job.JobOfferService;
import java.util.List;

@RestController
@RequestMapping("/joboffer")  // "/joboffer" 경로에 대한 요청을 처리하는 컨트롤러 클래스
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;  // JobOfferService 객체를 자동 주입하여 비즈니스 로직을 처리

    // 구인글 작성
    @PostMapping("/write.do")  // HTTP POST 방식으로 "/write.do" 경로에 접근하면 호출되는 메소드
    public ResponseEntity<String> jobOfferWrite(@RequestBody JobOfferDto jobOfferDto) {
        try {
            // JobOfferService를 사용하여 구인글 작성
            boolean isCreated = jobOfferService.jobOfferWrite(jobOfferDto);
            if (isCreated) {
                return new ResponseEntity<>("Job offer created successfully", HttpStatus.CREATED);  // 작성 성공 시 201 Created 반환
            } else {
                return new ResponseEntity<>("Failed to create job offer", HttpStatus.INTERNAL_SERVER_ERROR);  // 작성 실패 시 500 Internal Server Error 반환
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Bad request: " + e.getMessage(), HttpStatus.BAD_REQUEST);  // 잘못된 요청일 경우 400 Bad Request 반환
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // NullPointerException 처리
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // 예상치 못한 오류 처리
        }
    }

    // 구인글 전체 조회 - 지도 표시용 (특정 카테고리 서비스로 필터링)
    @GetMapping("/map.do")  // HTTP GET 방식으로 "/map.do" 경로에 접근하면 호출되는 메소드
    public ResponseEntity<List<JobOfferDto>> jobOfferMap(@RequestParam String joservice) {
        try {
            // joservice에 맞는 구인글 목록 조회
            List<JobOfferDto> jobOffers = jobOfferService.jobOfferMap(joservice);
            if (jobOffers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 조회된 구인글이 없을 경우 404 Not Found 반환
            }
            return new ResponseEntity<>(jobOffers, HttpStatus.OK);  // 구인글 목록을 반환하며 200 OK 반환
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 잘못된 요청 시 400 Bad Request 반환
        } catch (NullPointerException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // NullPointerException 발생 시 500 Internal Server Error 반환
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 기타 예외 발생 시 500 Internal Server Error 반환
        }
    }

    // 구인글 전체 조회 + 페이징 및 검색 기능
    @GetMapping("/findall.do")  // HTTP GET 방식으로 "/findall.do" 경로에 접근하면 호출되는 메소드
    public ResponseEntity<JobPageDto> jobOfferFindSearch(@RequestParam int page, @RequestParam String key, @RequestParam String keyword) {
        try {
            // 페이지네이션 및 검색 조건에 맞는 구인글 조회
            JobPageDto jobPageDto = jobOfferService.jobOfferFindSearch(page, key, keyword);
            return new ResponseEntity<>(jobPageDto, HttpStatus.OK);  // 구인글 목록을 반환하며 200 OK 반환
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 잘못된 요청 시 400 Bad Request 반환
        } catch (NullPointerException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // NullPointerException 발생 시 500 Internal Server Error 반환
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 기타 예외 발생 시 500 Internal Server Error 반환
        }
    }

    // 구인글 개별 조회 (특정 구인글을 조회)
    @GetMapping("/find.do")  // HTTP GET 방식으로 "/find.do" 경로에 접근하면 호출되는 메소드
    public ResponseEntity<JobOfferDto> jobOfferFind(@RequestParam int jono) {
        try {
            // 특정 구인글 조회
            JobOfferDto jobOffer = jobOfferService.jobOfferFind(jono);
            if (jobOffer == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // 구인글이 없을 경우 404 Not Found 반환
            }
            return new ResponseEntity<>(jobOffer, HttpStatus.OK);  // 구인글 정보를 반환하며 200 OK 반환
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 잘못된 요청 시 400 Bad Request 반환
        } catch (NullPointerException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // NullPointerException 발생 시 500 Internal Server Error 반환
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 기타 예외 발생 시 500 Internal Server Error 반환
        }
    }

    // 구인글 내용 수정
    @PutMapping("/update.do")  // HTTP PUT 방식으로 "/update.do" 경로에 접근하면 호출되는 메소드
    public ResponseEntity<String> jobOfferUpdate(@RequestBody JobOfferDto jobOfferDto) {
        try {
            // JobOfferService를 사용하여 구인글 수정
            boolean isUpdated = jobOfferService.jobOfferUpdate(jobOfferDto);
            if (isUpdated) {
                return new ResponseEntity<>("Job offer updated successfully", HttpStatus.OK);  // 수정 성공 시 200 OK 반환
            } else {
                return new ResponseEntity<>("Failed to update job offer", HttpStatus.INTERNAL_SERVER_ERROR);  // 수정 실패 시 500 Internal Server Error 반환
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Bad request: " + e.getMessage(), HttpStatus.BAD_REQUEST);  // 잘못된 요청 시 400 Bad Request 반환
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // NullPointerException 발생 시 500 Internal Server Error 반환
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // 예상치 못한 오류 발생 시 500 Internal Server Error 반환
        }
    }

    // 구인글 마감 상태 수정 API
    @PutMapping("/stateupdate.do")  // HTTP PUT 방식으로 "/stateupdate.do" 경로에 접근하면 호출되는 메소드
    public ResponseEntity<String> jobStateUpdate(@RequestParam int jono) {
        try {
            // 구인글의 마감 상태 업데이트
            boolean isUpdated = jobOfferService.jobStateUpdate(jono);
            if (isUpdated) {
                return new ResponseEntity<>("Job offer state updated successfully", HttpStatus.OK);  // 상태 업데이트 성공 시 200 OK 반환
            } else {
                return new ResponseEntity<>("Failed to update job offer state", HttpStatus.INTERNAL_SERVER_ERROR);  // 상태 업데이트 실패 시 500 Internal Server Error 반환
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Bad request: " + e.getMessage(), HttpStatus.BAD_REQUEST);  // 잘못된 요청 시 400 Bad Request 반환
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // NullPointerException 발생 시 500 Internal Server Error 반환
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // 예상치 못한 오류 발생 시 500 Internal Server Error 반환
        }
    }

    // 구인글 삭제 API
    @DeleteMapping("/delete.do")  // HTTP DELETE 방식으로 "/delete.do" 경로에 접근하면 호출되는 메소드
    public ResponseEntity<String> jobOfferDelete(@RequestParam int jono) {
        try {
            // 구인글 삭제
            boolean isDeleted = jobOfferService.jobOfferDelete(jono);
            if (isDeleted) {
                return new ResponseEntity<>("Job offer deleted successfully", HttpStatus.OK);  // 삭제 성공 시 200 OK 반환
            } else {
                return new ResponseEntity<>("Failed to delete job offer", HttpStatus.INTERNAL_SERVER_ERROR);  // 삭제 실패 시 500 Internal Server Error 반환
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Bad request: " + e.getMessage(), HttpStatus.BAD_REQUEST);  // 잘못된 요청 시 400 Bad Request 반환
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // NullPointerException 발생 시 500 Internal Server Error 반환
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  // 예상치 못한 오류 발생 시 500 Internal Server Error 반환
        }
    }

    // 내가 작성한 구인글 목록 조회
    @GetMapping("/mylist.do")  // HTTP GET 방식으로 "/mylist.do" 경로에 접근하면 호출되는 메소드
    public ResponseEntity<JobPageDto> jobofferMyList(@RequestParam int page, @RequestParam String key, @RequestParam String keyword) {
        try {
            // 내가 작성한 구인글 목록 조회
            JobPageDto jobPageDto = jobOfferService.jobOfferMyList(page, key, keyword);
            return new ResponseEntity<>(jobPageDto, HttpStatus.OK);  // 구인글 목록 반환하며 200 OK 반환
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 잘못된 요청 시 400 Bad Request 반환
        } catch (NullPointerException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // NullPointerException 발생 시 500 Internal Server Error 반환
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 기타 예외 발생 시 500 Internal Server Error 반환
        }
    }

    /* 페이징 형식 - 무한스크롤 처리 */
    // 구인글 무한 스크롤 조회 API
    @GetMapping("/scroll")
    public ResponseEntity<JobInfiniteScrollDto> getJobsWithScroll(
            @RequestParam String key,
            @RequestParam String keyword,
            @RequestParam(required = false) Integer lastId,
            @RequestParam(defaultValue = "5") int limit) {
        try {
            JobInfiniteScrollDto result = jobOfferService.getJobsInfiniteScroll(key, keyword, lastId, limit);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 내 구인글 무한 스크롤 조회 API
    @GetMapping("/myjobs/scroll")
    public ResponseEntity<JobInfiniteScrollDto> getMyJobsWithScroll(
            @RequestParam String key,
            @RequestParam String keyword,
            @RequestParam(required = false) Integer lastId,
            @RequestParam(defaultValue = "3") int limit) {
        try {
            JobInfiniteScrollDto result = jobOfferService.getMyJobsInfiniteScroll(key, keyword, lastId, limit);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



package webProject.service.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import webProject.model.dto.request.RequestDto;
import webProject.service.request.RequestService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PerformanceTestService {
    @Autowired
    private RequestService requestService;

    // 기존 방식 테스트
    public Map<String, Object> testOriginalMethod() {
        Map<String, Object> result = new HashMap<>();

        // 시작 시간 및 메모리 측정
        long startTime = System.currentTimeMillis();
        long startMemory = getUsedMemory();

        // 기존 메서드 호출
        List<RequestDto> requests = requestService.requestFindAll();

        // 종료 시간 및 메모리 측정
        long endTime = System.currentTimeMillis();
        long endMemory = getUsedMemory();

        // 결과 저장
        result.put("method", "original");
        result.put("executionTime", endTime - startTime);
        result.put("memoryUsed", (endMemory - startMemory) / (1024 * 1024));
        result.put("resultCount", requests.size());

        log.info("기존 방식: 실행 시간 {}ms, 메모리 사용 {}MB, 결과 수 {}개",
                endTime - startTime, (endMemory - startMemory) / (1024 * 1024), requests.size());

        return result;
    }

    // 페이징 방식 테스트
    public Map<String, Object> testPagedMethod(int page, int size) {
        Map<String, Object> result = new HashMap<>();

        // 시작 시간 및 메모리 측정
        long startTime = System.currentTimeMillis();
        long startMemory = getUsedMemory();

        // 페이징 메서드 호출
        Page<RequestDto> requests = requestService.getPagedRequests(page, size, "reqno", "DESC");

        // 종료 시간 및 메모리 측정
        long endTime = System.currentTimeMillis();
        long endMemory = getUsedMemory();

        // 결과 저장
        result.put("method", "paged");
        result.put("executionTime", endTime - startTime);
        result.put("memoryUsed", (endMemory - startMemory) / (1024 * 1024));
        result.put("resultCount", requests.getContent().size());
        result.put("totalElements", requests.getTotalElements());

        log.info("페이징 방식: 실행 시간 {}ms, 메모리 사용 {}MB, 페이지 결과 {}개, 전체 {}개",
                endTime - startTime, (endMemory - startMemory) / (1024 * 1024),
                requests.getContent().size(), requests.getTotalElements());

        return result;
    }

    // 두 방식 비교
    public Map<String, Object> comparePerformance() {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> originalResult = testOriginalMethod();
        Map<String, Object> pagedResult = testPagedMethod(0, 10);

        // 성능 향상률 계산
        long originalTime = (long) originalResult.get("executionTime");
        long pagedTime = (long) pagedResult.get("executionTime");
        double timeImprovement = (double) originalTime / Math.max(1, pagedTime);

        long originalMemory = (long) originalResult.get("memoryUsed");
        long pagedMemory = (long) pagedResult.get("memoryUsed");
        double memoryImprovement = (double) originalMemory / Math.max(1, pagedMemory);

        result.put("original", originalResult);
        result.put("paged", pagedResult);
        result.put("timeImprovement", timeImprovement);
        result.put("memoryImprovement", memoryImprovement);

        log.info("성능 향상률: 시간 {}배, 메모리 {}배",
                timeImprovement, memoryImprovement);

        return result;
    }

    // 사용 메모리 계산 헬퍼 메서드
    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
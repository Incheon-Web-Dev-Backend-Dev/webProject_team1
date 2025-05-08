package webProject.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webProject.service.test.PerformanceTestService;

import java.util.Map;

@RestController
@RequestMapping("/api/test/performance")
public class PerformanceTestController {
    @Autowired
    private PerformanceTestService performanceTestService;

    @GetMapping("/original")
    public ResponseEntity<Map<String, Object>> testOriginal() {
        return ResponseEntity.ok(performanceTestService.testOriginalMethod());
    }

    @GetMapping("/paged")
    public ResponseEntity<Map<String, Object>> testPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(performanceTestService.testPagedMethod(page, size));
    }

    @GetMapping("/compare")
    public ResponseEntity<Map<String, Object>> comparePerformance() {
        return ResponseEntity.ok(performanceTestService.comparePerformance());
    }
}
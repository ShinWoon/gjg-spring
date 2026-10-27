package com.newcld.fitlog.workout;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "피드백", description = "선생님 피드백 모아보기")
@RestController 
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }
    
    @Operation(summary = "고정 피드백 목록", description = "고정해 둔 피드백을 최신순으로 조회해요. 운동 전에 꼭 기억할 포인트를 한눈에 볼 때 써요.")
    @GetMapping("/pinned")
    public List<FeedbackResponse> pinned() {
        return service.findPinned();
    }

    @Operation(summary = "동작별 피드백 기록", description = "특정 동작에 대해 받은 피드백을 최신순으로 조회해요. 피드백이 비어 있는 기록은 빠져요. 없는 동작이면 404를 응답해요.")
    @GetMapping
    public List<FeedbackResponse> byExercise(
            @Parameter(description = "동작 ID", example = "1", required = true) @RequestParam Long exerciseId) {
        return service.findByExercise(exerciseId);
    }
}

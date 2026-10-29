package com.newcld.fitlog.workout;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "운동 동작", description = "스쿼트, 벤치프레스 같은 운동 동작 목록 관리. 운동 기록에서 여기 등록된 동작을 골라 써요.")
@RestController 
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService service;

    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    @Operation(summary = "동작 목록 조회", description = "등록된 동작을 이름순으로 조회해요. 부위를 지정하면 해당 부위 동작만 조회해요.")
    @GetMapping
    public List<ExerciseResponse> list(@Parameter(description = "운동 부위로 필터링 (생략하면 전체)", example = "LEGS")
    @RequestParam(required = false) BodyPart bodyPart) {
        return service.findAll(bodyPart).stream().map(ExerciseResponse::from).toList();
    }

    @Operation(summary = "동작 등록", description = "같은 이름의 동작이 이미 있으면 409, 없는 부위 값이면 400을 응답해요.")
    @PostMapping
    public ResponseEntity<ExerciseResponse> create(@Valid @RequestBody ExerciseRequest request) {
        Exercise saved = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ExerciseResponse.from(saved));
    }
    
    @Operation(summary = "동작 수정", description = "이름이나 부위를 바꿔요. 다른 동작과 이름이 겹치면 409, 없는 동작이면 404를 응답해요.")
    @PutMapping("/{id}")
    public ExerciseResponse update(@Parameter(description = "동작 ID", example = "1")
    @PathVariable Long id, @Valid @RequestBody ExerciseRequest request) {
        return ExerciseResponse.from(service.update(id, request));
    }

    @Operation(summary = "동작별 지난 기록", description = "세트 입력 화면에서 참고할 이 동작의 지난 기록을 최신순으로 조회해요. 세트가 없는 기록은 빠져요. 없는 동작이면 404, 조회 개수가 1~20을 벗어나면 400을 응답해요.")
    @GetMapping("/{id}/history")
    public List<ExerciseHistoryResponse> history(
            @Parameter(description = "동작 ID", example = "1") @PathVariable Long id,
            @Parameter(description = "조회할 기록 수 (1~20)", example = "5") @RequestParam(defaultValue = "5") int limit,
            @Parameter(description = "제외할 세션 ID. 지금 입력 중인 세션을 빼고 볼 때 사용 (생략 가능)", example = "12") @RequestParam(required = false) Long excludeSessionId) {
        return service.findHistory(id, limit, excludeSessionId);
    }

}

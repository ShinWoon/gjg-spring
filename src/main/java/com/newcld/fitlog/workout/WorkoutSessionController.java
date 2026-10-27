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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "운동 기록", description = "PT·개인운동 세션과 동작별 피드백, 세트 기록 관리")
@RestController
@RequestMapping("/api/sessions")
public class WorkoutSessionController {
    private final WorkoutSessionService service;

    public WorkoutSessionController(WorkoutSessionService service) {
        this.service = service;
    }

    @Operation(summary = "운동 세션 목록 조회", description = "전체 세션을 최신 날짜순으로 조회해요. 동작 기록은 포함되지 않아요.")
    @GetMapping
    public List<SessionSummaryResponse> list() {
        return service.findAll();
    }
    
    @Operation(summary = "운동 세션 상세 조회", description = "세션에 포함된 동작 기록과 세트까지 전부 조회해요. 없는 세션이면 404를 응답해요.")
    @GetMapping("/{id}")
    public SessionDetailResponse detail(@Parameter(description = "세션 ID", example = "1") @PathVariable Long id) {
        return service.findDetail(id);
    }

    @Operation(summary = "운동 세션 생성", description = "빈 세션을 만들어요. 동작은 생성 후 따로 추가해요. 같은 날짜에 여러 세션을 만들 수 있어요.")
    @PostMapping
    public ResponseEntity<SessionDetailResponse> create(@Valid @RequestBody SessionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "세션에 동작 추가", description = "세션에 동작 기록을 하나 추가해요. 순서 번호는 추가한 순서대로 자동으로 매겨져요. 세션이나 동작이 없으면 404를 응답해요.")
    @PostMapping("/{id}/exercises")
    public ResponseEntity<SessionDetailResponse> addExercise(@Parameter(description = "세션 ID", example = "1") @PathVariable Long id, @Valid @RequestBody ExerciseLogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addExerciseLog(id, request));
    }

    @Operation(summary = "동작의 세트 목록 교체", description = "해당 동작 기록의 세트를 보낸 목록으로 통째로 바꿔요. 세트 추가·수정·삭제를 모두 이 API로 처리해요. "
                    + "세트 번호는 목록 순서대로 1부터 다시 매겨져요. 동작 기록이 해당 세션 소속이 아니면 404를 응답해요.")
    @PutMapping("/{sessionId}/exercises/{logId}/sets")
    public SessionDetailResponse replaceSets(@Parameter(description = "세션 ID", example = "1") @PathVariable Long sessionId, @Parameter(description = "동작 기록 ID (세션 상세의 exercises 안의 id)", example = "1")  @PathVariable Long logId, @Valid @RequestBody SetsRequest request) {
        return service.replaceSets(sessionId, logId, request);
    }

    @Operation(summary = "운동 세션 수정", description = "날짜, 운동 종류, 메모를 바꿔요. 없는 세션이면 404를 응답해요.")
    @PutMapping("/{id}")
    public SessionDetailResponse update(
            @Parameter(description = "세션 ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody SessionRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "운동 세션 삭제", description = "세션에 포함된 동작 기록과 세트까지 모두 삭제돼요. 없는 세션이면 404를 응답해요.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "세션 ID", example = "1") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "동작 기록 피드백 수정", description = "피드백 내용과 고정 여부를 바꿔요. 동작 기록이 해당 세션 소속이 아니면 404를 응답해요.")
    @PutMapping("/{sessionId}/exercises/{logId}")
    public SessionDetailResponse updateExerciseLog(
            @Parameter(description = "세션 ID", example = "1") @PathVariable Long sessionId,
            @Parameter(description = "동작 기록 ID", example = "1") @PathVariable Long logId,
            @Valid @RequestBody ExerciseLogUpdateRequest request) {
        return service.updateExerciseLog(sessionId, logId, request);
    }

    @Operation(summary = "동작 기록 삭제", description = "동작 기록과 세트를 삭제하고, 남은 동작의 순서를 1부터 다시 매겨요.")
    @DeleteMapping("/{sessionId}/exercises/{logId}")
    public SessionDetailResponse deleteExerciseLog(
            @Parameter(description = "세션 ID", example = "1") @PathVariable Long sessionId,
            @Parameter(description = "동작 기록 ID", example = "1") @PathVariable Long logId) {
        return service.deleteExerciseLog(sessionId, logId);
    }
}

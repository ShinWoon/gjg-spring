package com.newcld.fitlog.routine;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newcld.fitlog.workout.SessionDetailResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "루틴", description = "개인운동용 루틴 관리. PT 기록으로 루틴을 만들고, 루틴으로 개인운동을 시작해요.")
@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    private final RoutineService service;

    public RoutineController(RoutineService service) {
        this.service = service;
    }

    @Operation(summary = "루틴 목록 조회", description = "최근에 만든 순서로 조회해요.")
    @GetMapping
    public List<RoutineSummaryResponse> list() {
        return service.findAll();
    }

    @Operation(summary = "루틴 상세 조회", description = "동작 목록과 목표 세트·횟수·무게까지 조회해요.")
    @GetMapping("/{id}")
    public RoutineDetailResponse detail(
            @Parameter(description = "루틴 ID", example = "1") @PathVariable Long id) {
        return service.findDetail(id);
    }

    @Operation(summary = "루틴 직접 만들기", description = "동작 목록을 직접 지정해서 루틴을 만들어요. 없는 동작이 있으면 404를 응답해요.")
    @PostMapping
    public ResponseEntity<RoutineDetailResponse> create(@Valid @RequestBody RoutineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "운동 기록으로 루틴 만들기",
            description = "PT 기록의 동작을 복사해서 루틴을 만들어요. 동작마다 세트 수와 가장 무거운 세트의 무게·횟수가 목표값이 되고, "
                    + "피드백은 메모로 복사돼요. 동작이 없는 기록이면 400을 응답해요.")
    @PostMapping("/from-session/{sessionId}")
    public ResponseEntity<RoutineDetailResponse> createFromSession(
            @Parameter(description = "루틴으로 만들 운동 기록(세션) ID", example = "1") @PathVariable Long sessionId,
            @Valid @RequestBody RoutineFromSessionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createFromSession(sessionId, request));
    }

    @Operation(summary = "루틴 수정", description = "이름, 메모와 동작 목록을 통째로 바꿔요. 이미 이 루틴으로 한 운동 기록은 바뀌지 않아요.")
    @PutMapping("/{id}")
    public RoutineDetailResponse update(
            @Parameter(description = "루틴 ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody RoutineRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "루틴 삭제", description = "루틴을 삭제해도 이 루틴으로 한 운동 기록은 그대로 남아요.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "루틴 ID", example = "1") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "루틴으로 개인운동 시작",
            description = "루틴을 복사해서 개인운동 기록을 만들어요. 목표 세트가 미리 채워져 있어서 실제로 한 무게·횟수만 고치면 돼요. "
                    + "세트 수정은 운동 기록의 세트 교체 API를 쓰세요.")
    @PostMapping("/{id}/start")
    public ResponseEntity<SessionDetailResponse> start(
            @Parameter(description = "루틴 ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody StartRoutineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.start(id, request));
    }
}
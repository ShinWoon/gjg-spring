package com.newcld.fitlog.meal;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "식단 기록", description = "끼니별로 먹은 내용과 가이드 준수 여부 기록. 같은 끼니에 여러 번 기록할 수 있어요.")
@RestController
@RequestMapping("/api/meal-logs")
public class MealLogController {

    private final MealLogService service;

    public MealLogController(MealLogService service) {
        this.service = service;
    }

    @Operation(summary = "기간별 식단 기록", description = "기간 안의 기록을 최신 날짜순, 같은 날은 아침→점심→저녁→간식 순으로 조회해요. 하루만 볼 때는 from과 to를 같은 날짜로 보내요. 날짜가 빠졌거나 from이 to보다 늦거나 62일을 넘으면 400을 응답해요.")
    @GetMapping
    public List<MealLogResponse> list(
            @Parameter(description = "시작 날짜 (포함)", example = "2026-10-27", required = true) @RequestParam LocalDate from,
            @Parameter(description = "끝 날짜 (포함)", example = "2026-11-02", required = true) @RequestParam LocalDate to) {
        return service.findByRange(from, to);
    }

    @Operation(summary = "식단 기록 등록", description = "내용이 비었거나 없는 끼니 값이면 400을 응답해요.")
    @PostMapping
    public ResponseEntity<MealLogResponse> create(@Valid @RequestBody MealLogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "식단 기록 수정", description = "없는 기록이면 404를 응답해요. 가이드 준수 여부를 생략하면 null(판단 안 함)로 바뀌어요.")
    @PutMapping("/{id}")
    public MealLogResponse update(
            @Parameter(description = "식단 기록 ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody MealLogRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "식단 기록 삭제", description = "없는 기록이면 404를 응답해요.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "식단 기록 ID", example = "1") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

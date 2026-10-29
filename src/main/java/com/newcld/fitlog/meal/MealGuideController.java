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

@Tag(name = "식단 가이드", description = "선생님이 정해 준 끼니별 식단 가이드. 같은 끼니에 새 가이드를 등록하면 적용 시작일부터 새 가이드로 바뀌고, 이전 가이드는 이력으로 남아요.")
@RestController
@RequestMapping("/api/meal-guides")
public class MealGuideController {

    private final MealGuideService service;

    public MealGuideController(MealGuideService service) {
        this.service = service;
    }

    @Operation(summary = "가이드 전체 이력", description = "등록된 가이드를 적용 시작일 최신순으로 조회해요.")
    @GetMapping
    public List<MealGuideResponse> list() {
        return service.findAll();
    }

    @Operation(summary = "날짜 기준 현재 가이드", description = "해당 날짜에 적용되는 끼니별 가이드를 아침→점심→저녁→간식 순으로 조회해요. 그 날짜 이전에 시작된 가이드가 없는 끼니는 빠져요. 날짜가 없거나 형식이 틀리면 400을 응답해요.")
    @GetMapping("/current")
    public List<MealGuideResponse> current(
            @Parameter(description = "기준 날짜 (앱의 오늘 날짜)", example = "2026-10-29", required = true) @RequestParam LocalDate date) {
        return service.findCurrent(date);
    }

    @Operation(summary = "가이드 등록", description = "같은 끼니에 같은 적용 시작일인 가이드가 이미 있으면 409, 없는 끼니 값이면 400을 응답해요.")
    @PostMapping
    public ResponseEntity<MealGuideResponse> create(@Valid @RequestBody MealGuideRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "가이드 수정", description = "다른 가이드와 끼니·적용 시작일이 겹치면 409, 없는 가이드면 404를 응답해요.")
    @PutMapping("/{id}")
    public MealGuideResponse update(
            @Parameter(description = "가이드 ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody MealGuideRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "가이드 삭제", description = "없는 가이드면 404를 응답해요.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "가이드 ID", example = "1") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

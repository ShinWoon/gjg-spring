package com.newcld.fitlog.checkin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "주간 체크인", description = "매주 월요일 공복 몸무게와 메모 기록")
@RestController
@RequestMapping("/api/checkins")
@RequiredArgsConstructor
public class WeeklyCheckinController {
    private final WeeklyCheckinService service;

    @Operation(summary = "체크인 목록 조회", description = "전체 체크인을 최신 날짜순으로 조회해요.")
    @GetMapping
    public List<CheckinResponse> list() {
        return service.findAll().stream().map(CheckinResponse::from).toList();
    }

    @Operation(summary = "체크인 등록", description = "같은 날짜에 이미 기록이 있으면 409를 응답해요.")
    @PostMapping
    public ResponseEntity<CheckinResponse> create(@Valid @RequestBody CheckinForm form) {
        WeeklyCheckin saved = service.create(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(CheckinResponse.from(saved));
    }

    @Operation(summary = "최근 체크인 조회", description = "가장 최근 체크인 하나를 조회해요. 몸무게 입력칸에 지난 기록을 미리 채울 때 써요. 기록이 없으면 204를 응답해요.")
    @GetMapping("/latest")
    public ResponseEntity<CheckinResponse> latest() {
        return service.findLatest()
                .map(c -> ResponseEntity.ok(CheckinResponse.from(c)))
                .orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "체크인 수정", description = "다른 기록과 날짜가 겹치면 409, 없는 기록이면 404를 응답해요.")
    @PutMapping("/{id}")
    public CheckinResponse update(@Parameter(description = "수정할 체크인 ID (목록 조회에서 확인)", example = "1") @PathVariable Long id, @Valid @RequestBody CheckinForm form) {
        return CheckinResponse.from(service.update(id, form));
    }

    @Operation(summary = "체크인 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "삭제할 체크인 ID (목록 조회에서 확인)", example = "1") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

package com.newcld.fitlog.checkin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/checkins")
@RequiredArgsConstructor
public class WeeklyCheckinController {
    private final WeeklyCheckinService service;

    @GetMapping
    public List<CheckinResponse> list() {
        return service.findAll().stream().map(CheckinResponse::from).toList();
    }

    @PostMapping
    public ResponseEntity<CheckinResponse> create(@Valid @RequestBody CheckinForm form) {
        WeeklyCheckin saved = service.create(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(CheckinResponse.from(saved));
    }

    @GetMapping("/latest")
    public ResponseEntity<CheckinResponse> latest() {
       return service.findLatest()
       .map(c -> ResponseEntity.ok(CheckinResponse.from(c)))
       .orElse(ResponseEntity.noContent().build()); 
    }

    @PutMapping("/{id}")
    public CheckinResponse update(@PathVariable Long id, @Valid @RequestBody CheckinForm form) {
        return CheckinResponse.from(service.update(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}

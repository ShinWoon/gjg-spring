package com.newcld.fitlog.workout;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/sessions")
public class WorkoutSessionController {
    private final WorkoutSessionService service;

    public WorkoutSessionController(WorkoutSessionService service) {
        this.service = service;
    }

    @GetMapping
    public List<SessionSummaryResponse> list() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public SessionDetailResponse detail(@PathVariable Long id) {
        return service.findDetail(id);
    }

    @PostMapping
    public ResponseEntity<SessionDetailResponse> create(@Valid @RequestBody SessionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PostMapping("/{id}/exercises")
    public ResponseEntity<SessionDetailResponse> addExercise(@PathVariable Long id, @Valid @RequestBody ExerciseLogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addExerciseLog(id, request));
    }
}

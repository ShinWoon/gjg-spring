package com.newcld.fitlog.workout;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController 
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseService service;

    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    @GetMapping
    public List<ExerciseResponse> list(@RequestParam(required = false) BodyPart bodyPart) {
        return service.findAll(bodyPart).stream().map(ExerciseResponse::from).toList();
    }

    @PostMapping
    public ResponseEntity<ExerciseResponse> create(@Valid @RequestBody ExerciseRequest request) {
        Exercise saved = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ExerciseResponse.from(saved));
    }
    
    @PutMapping("/{id}")
    public ExerciseResponse update(@PathVariable Long id, @Valid @RequestBody ExerciseRequest request) {
        return ExerciseResponse.from(service.update(id, request));
    }
    
}

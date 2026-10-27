package com.newcld.fitlog.routine;

import java.util.ArrayList;
import java.util.List;

import com.newcld.fitlog.workout.Exercise;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 1_000)
    private String memo;

    private Long sourceSessionId;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderNo ASC")
    private List<RoutineItem> items = new ArrayList<>();

    public Routine(String name, String memo, Long sourceSessionId) {
        this.name = name;
        this.memo = memo;
        this.sourceSessionId = sourceSessionId;
    }

    public void update(String name, String memo) {
        this.name = name;
        this.memo = memo;
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(Exercise exercise, int targetSets, Integer targetReps, Double targetWeightKg, String note) {
        items.add(new RoutineItem(this, exercise, items.size() + 1, targetSets, targetReps, targetWeightKg, note));
    }
}

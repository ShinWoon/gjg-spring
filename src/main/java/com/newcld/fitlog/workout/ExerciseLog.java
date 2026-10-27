package com.newcld.fitlog.workout;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // fetch = FetchType.LAZY는 동작 기록을 조회할 때 세션이나 동작 정보를 실제로 필요해질 때까지 DB에서 가져오지 않는 설정
    // @ManyToOne에는 항상 LAZY를 붙이는 게 정석
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id")
    private WorkoutSession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(nullable = false)
    private int orderNo;

    @Column(length = 2_000)
    private String feedback;

    private boolean pinned;

    ExerciseLog(WorkoutSession session, Exercise exercise, int orderNo, String feedback) {
        this.session = session;
        this.exercise = exercise;
        this.orderNo = orderNo;
        this.feedback = feedback;
    }

    @OneToMany(mappedBy = "exerciseLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("setNo ASC")
    private List<SetLog> sets = new ArrayList<>();

    public void clearSets() {
        sets.clear();
    }

    public void addSet(Double weightKg, int reps) {
        sets.add(new SetLog(this, sets.size() + 1, weightKg, reps));
    }

    public void updateFeedback(String feedback, boolean pinned) {
        this.feedback = feedback;
        this.pinned = pinned;
    }

    void changeOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}

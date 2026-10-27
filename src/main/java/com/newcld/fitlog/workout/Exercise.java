package com.newcld.fitlog.workout;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BodyPart bodyPart;

    public Exercise(String name, BodyPart bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
    }

    public void update(String name, BodyPart bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
    }
}

package com.newcld.fitlog.meal;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcld.fitlog.common.DuplicateException;
import com.newcld.fitlog.common.NotFoundException;

@Service
@Transactional(readOnly = true)
public class MealGuideService {
    private final MealGuideRepository repository;

    public MealGuideService(MealGuideRepository repository) {
        this.repository = repository;
    }

    public List<MealGuideResponse> findAll() {
        return repository.findAllByOrderByValidFromDescIdDesc().stream().map(MealGuideResponse::from).toList();
    }

    public List<MealGuideResponse> findCurrent(LocalDate date) {
        // DB에는 enum이 문자열로 저장돼 정렬하면 알파벳순이 되므로, 아침→점심→저녁→간식 순서는 여기서 맞춰요
        return repository.findCurrent(date).stream()
        .sorted(Comparator.comparing(MealGuide::getMealType))
        .map(MealGuideResponse::from)
        .toList();
    }

    @Transactional
    public MealGuideResponse create(MealGuideRequest request) {
        if(repository.existsByMealTypeAndValidFrom(request.mealType(), request.validFrom())) {
            throw new DuplicateException("같은 끼니에 같은 날짜로 시작하는 가이드가 이미 있어요.");
        }
        MealGuide saved = repository.save(new MealGuide(request.mealType(), request.validFrom(), request.content().trim()));
        return MealGuideResponse.from(saved);
    }

    @Transactional
    public MealGuideResponse update(Long id, MealGuideRequest request) {
        MealGuide guide = findGuide(id);

        boolean keyChanged = guide.getMealType() != request.mealType() || !guide.getValidFrom().equals(request.validFrom());
        if(keyChanged && repository.existsByMealTypeAndValidFrom(request.mealType(), request.validFrom())) {
            throw new DuplicateException("같은 끼니에 같은 날짜로 시작하는 가이드가 이미 있어요.");
        }

        guide.update(request.mealType(), request.validFrom(), request.content().trim());
        return MealGuideResponse.from(guide);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findGuide(id));
    }

    private MealGuide findGuide(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("식단 가이드를 찾을 수 없어요."));
    }
}

package com.newcld.fitlog.meal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcld.fitlog.common.BadRequestException;
import com.newcld.fitlog.common.NotFoundException;

@Service
@Transactional(readOnly = true)
public class MealLogService {
    // 6주짜리 달력 화면(42일)도 한 번에 조회할 수 있는 범위
    private static final int MAX_RANGE_DAYS = 62;

    // 최신 날짜부터, 같은 날은 아침→점심→저녁→간식, 같은 끼니는 등록순
    private static final Comparator<MealLog> ORDER = Comparator.comparing(MealLog::getMealDate).reversed()
            .thenComparing(MealLog::getMealType)
            .thenComparing(MealLog::getId);

    private final MealLogRepository repository;

    public MealLogService(MealLogRepository repository) {
        this.repository = repository;
    }

    public List<MealLogResponse> findByRange(LocalDate from, LocalDate to) {
        if(from.isAfter(to)) {
            throw new BadRequestException("시작 날짜가 끝 날짜보다 늦어요.");
        }
        if(ChronoUnit.DAYS.between(from, to) + 1 > MAX_RANGE_DAYS) {
            throw new BadRequestException("한 번에 " + MAX_RANGE_DAYS + "일까지 조회할 수 있어요.");
        }
        return repository.findAllByMealDateBetween(from, to).stream()
        .sorted(ORDER)
        .map(MealLogResponse::from)
        .toList();
    }

    @Transactional
    public MealLogResponse create(MealLogRequest request) {
        MealLog saved = repository.save(new MealLog(request.mealDate(), request.mealType(), request.content().trim(), request.followedGuide()));
        return MealLogResponse.from(saved);
    }

    @Transactional
    public MealLogResponse update(Long id, MealLogRequest request) {
        MealLog log = findLog(id);
        log.update(request.mealDate(), request.mealType(), request.content().trim(), request.followedGuide());
        return MealLogResponse.from(log);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findLog(id));
    }

    private MealLog findLog(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("식단 기록을 찾을 수 없어요."));
    }
}

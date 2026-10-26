package com.newcld.fitlog.checkin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newcld.fitlog.common.DuplicateException;
import com.newcld.fitlog.common.NotFoundException;


@Service 
@Transactional (readOnly = true)
public class WeeklyCheckinService {
    private final WeeklyCheckinRepository repository;

    public WeeklyCheckinService(WeeklyCheckinRepository repository) {
        this.repository = repository;
    }

    public List<WeeklyCheckin> findAll() {
        return repository.findAllByOrderByCheckDateDesc();
    }

    public Optional<WeeklyCheckin> findLatest() {
        return repository.findTopByOrderByCheckDateDesc();
    }

    @Transactional 
    public WeeklyCheckin create(CheckinForm form) {
        if(repository.existsByCheckDate(form.getCheckDate())) {
            throw new DuplicateException("이미 기록된 날짜예요.");
        }
        return repository.save(new WeeklyCheckin(form.getCheckDate(), form.getWeightKg(), form.getMemo()));
    }

    @Transactional
    public WeeklyCheckin update(Long id, CheckinForm form) {
        WeeklyCheckin checkin = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("체크인 기록을 찾을 수 없어요."));

        boolean dateChanged = !checkin.getCheckDate().equals(form.getCheckDate());
        if(dateChanged && repository.existsByCheckDate(form.getCheckDate())) {
            throw new DuplicateException("이미 기록된 날짜예요.");
        }

        checkin.update(form.getCheckDate(), form.getWeightKg(), form.getMemo());
        return checkin;
    }

    @Transactional 
    public void delete(Long id) {
        WeeklyCheckin checkin = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("체크인 기록을 찾울 수 없어요."));
        repository.delete(checkin);
    }
}

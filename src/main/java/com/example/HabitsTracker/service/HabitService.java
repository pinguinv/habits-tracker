package com.example.HabitsTracker.service;

import com.example.HabitsTracker.mappers.HabitMapper;
import com.example.HabitsTracker.models.Habit;
import com.example.HabitsTracker.models.HabitDone;
import com.example.HabitsTracker.models.dto.HabitDto;
import com.example.HabitsTracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> findAll(){
        return (List<Habit>) habitRepository.findAll();
    }

    public Optional<Habit> findById(Long id){
        return habitRepository.findById(id);
    }

    public HabitDto save(HabitDto habit) {
        Habit entity;
        if (isNull(habit.getId())) {
            entity = HabitMapper.INSTANCE.map(habit);
            entity.setCreated(LocalDate.now());
            entity.setActive(habit.isActive());
        } else {
            entity = HabitMapper.INSTANCE.map(habit,
                    habitRepository.findById(habit.getId()).orElseThrow(
                            () -> new RuntimeException(
                                    String.format("Habit with id: %s not found", habit.getId())
                            )
                    )
            );

        }

        return HabitMapper.INSTANCE.map(habitRepository.save(entity));
    }

    public void deleteById(Long id){
        habitRepository.deleteById(id);
    }

    public void deleteAll(){
        habitRepository.deleteAll();
    }

    public List<Habit> findAllByStatus(IsDone status){
        List<Habit> result = new ArrayList<>();
        List<Habit> all = (List<Habit>) habitRepository.findAll();

        switch (status){
            case Done:
                for (int i = 0; i < all.size(); i++){
                    if (all.get(i).getHabitDoneList().size() > 0){
                        HabitDone lastDoneRep =  all.get(i).getHabitDoneList().get(
                                all.get(i).getHabitDoneList().size() - 1);
                        if ( all.get(i).getNumReps() <= lastDoneRep.getNumRepsDone() ){
                            result.add(all.get(i));
                        }
                    }

                }
                break;
            case InProgress:
                for (int i = 0; i < all.size(); i++){
                    if (all.get(i).getHabitDoneList().size() > 0){
                        HabitDone lastDoneRep =  all.get(i).getHabitDoneList().get(
                                all.get(i).getHabitDoneList().size() - 1);
                        if ( all.get(i).getNumReps() > lastDoneRep.getNumRepsDone() ){
                            result.add(all.get(i));
                        }
                    } else {
                        result.add(all.get(i));
                    }
                }
                break;
        }

        return result;
    }


}

package com.example.HabitsTracker.service;

import com.example.HabitsTracker.models.Habit;
import com.example.HabitsTracker.models.HabitDone;
import com.example.HabitsTracker.repository.HabitDoneRepository;
import com.example.HabitsTracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HabitDoneService {

    private final HabitRepository habitRepository;
    private final HabitDoneRepository habitDoneRepository;

    public HabitDoneService(HabitRepository habitRepository,
                            HabitDoneRepository habitDoneRepository) {
        this.habitRepository = habitRepository;
        this.habitDoneRepository = habitDoneRepository;
    }

    public HabitDone addHabitDone(Long habitId){

        HabitDone habitDone = new HabitDone();

        habitDone.setCreated(LocalDate.now());

        habitDone.setHabit(habitRepository.
                findById(habitId).orElse(null));

        habitDone.setStart(LocalDateTime.now());

        return habitDoneRepository.save(habitDone);
    }



    public List<HabitDone> findAllDoneHabits(){
        return (List<HabitDone>) habitDoneRepository.findAll();
    }

    public List<HabitDone> findDoneByHabitIdAndStatus(Long habitId, IsDone status){
        List<HabitDone> habitDoneList = habitDoneRepository.findByHabit_Id(habitId);

        List<HabitDone> result = new ArrayList<>();

        switch (status){
            case Done:
                // jeśli habitDone posiada End != null to znaczy że jest zrobione
                for (int i = 0; i < habitDoneList.size(); i++){
                    if (habitDoneList.get(i).getEnd() != null){
                        result.add(habitDoneList.get(i));
                    }
                }

                break;
            case InProgress:
                // jeśli w habitDone End == null to znaczy że nie jest skończone/trwa

                for (int i = 0; i < habitDoneList.size(); i++){
                    if (habitDoneList.get(i).getEnd() == null){
                        result.add(habitDoneList.get(i));
                    }
                }

                break;
        }

        return result;
    }


    public List<HabitDone> findByCreated(String createdStr){

        LocalDate created = null;

        if (!createdStr.isEmpty()){

            created = LocalDate.parse(createdStr);

        } else {
            created = LocalDate.now();
        }

        return habitDoneRepository.findAllByCreated(created);
    }

    public String[][] getLastMonthDoneList(){
        String result[][] = new String[29][2];
        LocalDate today = LocalDate.now();
        LocalDate day = today.minusWeeks(4);
        int i = 0;
        while (day.isBefore(today) || day.isEqual(today)){
            result[i][0] = day.toString();

            int doneListLength = findByCreated(day.toString()).size();

            result[i][1] = String.valueOf(doneListLength);
            day = day.plusDays(1);
            i++;
        }

        return result;
    }

    public String[][] getLastMonthDoneListForEachHabit(){
        List<Habit> habitsArr = (List<Habit>) habitRepository.findAll();

        String result[][] = new String[habitsArr.size()][29];
        LocalDate today = LocalDate.now();

        for (int i = 0; i < habitsArr.size(); i++){
            LocalDate day = today.minusWeeks(4);
            int dayNum = 0;
            while (day.isBefore(today) || day.isEqual(today)){

                int doneListLength = 0;

                for (int j = 0; j < habitsArr.get(i).getHabitDoneList().size(); j++){
                    if (day.isEqual(habitsArr.get(i).getHabitDoneList().get(j).getCreated())){
                        doneListLength++;
                    }
                }

                result[i][dayNum++] = String.valueOf(doneListLength);
                day = day.plusDays(1);

            }
        }


        return result;
    }


}

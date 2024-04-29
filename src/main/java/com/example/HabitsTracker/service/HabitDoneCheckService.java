package com.example.HabitsTracker.service;


import com.example.HabitsTracker.models.Habit;
import com.example.HabitsTracker.models.HabitDone;
import com.example.HabitsTracker.repository.HabitDoneRepository;
import com.example.HabitsTracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HabitDoneCheckService {

    private final HabitRepository habitRepository;
    private final HabitDoneRepository habitDoneRepository;

    public HabitDoneCheckService(HabitRepository habitRepository,
                                 HabitDoneRepository habitDoneRepository) {
        this.habitRepository = habitRepository;
        this.habitDoneRepository = habitDoneRepository;
    }

    // metoda wywoływana kiedy użytkownik skończy wykonywać nawyk
    public String isHabitDoneCheckAndSaveOrDeleteById(Long habitId){

        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new RuntimeException("No such habit"));
        boolean active = habit.isActive();
        String result = "Habit is not active";
        if (active){

            result = "Is habit done and saved: ";

            // lista zrobionych powtórzeń nawyku
            List<HabitDone> habitDoneList = habitDoneRepository.findByHabit_Id(habitId);
            // zaczęte powtórzenie, które będzie zapisane jeśli zostało wykonane
            HabitDone habitDone = habitDoneList.get(habitDoneList.size()-1);

            // zakończenie powtórzenia
            habitDone.setEnd(LocalDateTime.now());


            // numer tego powtórzenia
            int numOfRep = checkIfPeriodIsFinished(habit, habitDone, habitDoneList);

            if (numOfRep < 0){
                habitDoneRepository.deleteById(habitDone.getId());
                return "You've already done all reps for this period";
            }


            boolean durationTimeOfRepFlag = false;

            durationTimeOfRepFlag = durationTimeFlag(habit.getDurationTime(),
                    Optional.of(habitDone.getStart()),
                    Optional.of(habitDone.getEnd()));


            if ( durationTimeOfRepFlag ){ // jeśli powtórzenie trwało
                // wystarczająco długo, żeby je zaliczyć
                habitDone.setNumRepsDone(numOfRep);

                habitDoneRepository.save(habitDone);
                result += true;
            }else {
                habitDoneRepository.deleteById(habitDone.getId());
                result += false;
            }
        }

        return result;
    }

    private int checkIfPeriodIsFinished(Habit habit, HabitDone habitDone,
                                        List<HabitDone> habitDoneList){
        int numOfRep = 1;
        HabitDone prevRep = null;

        if (habitDoneList.size() >= 2){

            prevRep = habitDoneList.get(habitDoneList.size()-2);

            LocalDate today = LocalDate.now();


            switch (habit.getPeriod()){
                case DAILY:
                    if( habitDone.getCreated().isAfter(prevRep.getCreated()) ){
                        numOfRep = 1; // jeśli zmieniła się data, zostanie zaczęty nowy okres
                    } else if( habitDone.getCreated().isEqual(prevRep.getCreated()) ){
                        if ( prevRep.getNumRepsDone() >= habit.getNumReps() ){
                            numOfRep = -1; // jeśli wszystkie powtórzenia w tym okresie zrobione
                        } else {
                            numOfRep = prevRep.getNumRepsDone() + 1;
                            // jeśli nie wszystkie powtórzenia w tym okresie zrobione doda kolejne
                        }
                    }


                    break;
                case WEEKLY:

                    List<HabitDone> habitDoneListWhereNumRepsIsOne = new ArrayList<>();

                    habitDoneList.stream().forEach(habitDoneForWeekly -> {
                        if (habitDoneForWeekly.getNumRepsDone() == 1){
                            habitDoneListWhereNumRepsIsOne.add(habitDoneForWeekly);
                        }
                    });

                    LocalDate createdForWeekly = habitDoneListWhereNumRepsIsOne.get(
                                habitDoneListWhereNumRepsIsOne.size()-1).getCreated();

                    LocalDate createdForWeeklyPlusWeek =
                            createdForWeekly.plusWeeks(1);

                    // jeśli minął tydzień od ostatniego powtórzenia gdzie numReps == 1
                    // liczy powtórzenia od nowa
                    if (today.isAfter(createdForWeeklyPlusWeek)){
                        numOfRep = 1;
                    } else {
                        if ( prevRep.getNumRepsDone() >= habit.getNumReps() ){
                            numOfRep = -1; // jeśli wszystkie powtórzenia w tym okresie zrobione
                        } else {
                            numOfRep = prevRep.getNumRepsDone() + 1;
                            // jeśli nie wszystkie powtórzenia w tym okresie zrobione doda kolejne
                        }
                    }

                    break;
            }


        }

        return numOfRep;
    }


    // sprawdzenie czy użytkownik robił powtórzenia wystarczająco długo
    private boolean durationTimeFlag(int durationTimeStandard,
                                     Optional<LocalDateTime> start,
                                     Optional<LocalDateTime> end){
        boolean result = false;
        int duration = 0;

        if (start.isPresent() && end.isPresent()){

            long longStart = Timestamp.valueOf(start.get()).getTime();
            long longEnd = Timestamp.valueOf(end.get()).getTime();
            long difference = longEnd - longStart;
            duration = ((int) difference/60000) + 1;
            /*
            dodaje 1 minutę do czasu trwania dla pewności, bo użytkownik może zacząć np.
            o 10:30:47 i gdyby chciał zapisać półgodzinne powtórzenie np. o 11:00:04,
            to bez tej dodanej minuty w praktyce mu nie zaliczy
            */
        }

        if(duration >= durationTimeStandard){
            result = true;
        }

        return result;
    }

}

package com.example.HabitsTracker.controllers;

import com.example.HabitsTracker.models.Habit;
import com.example.HabitsTracker.models.HabitDone;
import com.example.HabitsTracker.models.dto.HabitDto;
import com.example.HabitsTracker.service.HabitDoneService;
import com.example.HabitsTracker.service.HabitDoneCheckService;
import com.example.HabitsTracker.service.HabitService;
import com.example.HabitsTracker.service.IsDone;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/habits")
public class HabitsApiController {

    private final HabitService habitService;

    private final HabitDoneCheckService habitDoneCheckService;

    private final HabitDoneService habitDoneService;


    public HabitsApiController(HabitService habitService,
                               HabitDoneCheckService habitDoneCheckService,
                               HabitDoneService habitDoneService) {
        this.habitService = habitService;
        this.habitDoneCheckService = habitDoneCheckService;
        this.habitDoneService = habitDoneService;
    }

    @GetMapping("/addDone")
    public HabitDone addDone(@RequestParam Long habitId){
        return habitDoneService.addHabitDone(habitId);
    }


    /*
    sprawdzanie czy ostatnie powtórzenie zostało zakończone
    */
    @GetMapping("find/{habitId}/{status}")
    public List<HabitDone> findDoneByHabitIdAndStatus(
            @PathVariable Long habitId,
            @PathVariable IsDone status){
        return habitDoneService.findDoneByHabitIdAndStatus(habitId, status);
    }

    @GetMapping("/findAllByStatus/{status}")
    public List<Habit> findAllByStatus(@PathVariable IsDone status){
        return habitService.findAllByStatus(status);
    }

    @GetMapping("/allDone")
    public List<HabitDone> findAllDoneHabits(){
        return habitDoneService.findAllDoneHabits();
    }


    @GetMapping("/isHabitDoneCheckAndSaveOrDeleteById")
    public String isHabitDoneById(
            @RequestParam
            Long habitId){
        return habitDoneCheckService.isHabitDoneCheckAndSaveOrDeleteById(habitId);
    }


    @GetMapping("/findDoneByCreated")
    public List<HabitDone> findByCreated(
            @RequestParam(required = false, defaultValue = "")
            String createdStr){


        return habitDoneService.findByCreated(createdStr);
    }

    @GetMapping("/getLastMonthDoneList")
    public String[][] getLastMonthCountByDayDoneList(){
        return habitDoneService.getLastMonthDoneList();
    }

    @GetMapping("/getLastMonthDoneListForEachHabit")
    public String[][] getLastMonthDoneListById(){
        return habitDoneService.getLastMonthDoneListForEachHabit();
    }



    @GetMapping("/all")
    public List<Habit> findAll(){
        return habitService.findAll();
    }

    @GetMapping("/findById")
    public Optional<Habit> findById(
            @RequestParam(defaultValue = "1")
            Long id){
        return habitService.findById(id);
    }

    @PostMapping("/save")
    public HabitDto save(@RequestBody HabitDto habit){
        return habitService.save(habit);
    }

    @GetMapping("/deleteById")
    public void deleteById(@RequestParam Long id){
        habitService.deleteById(id);
    }

    @GetMapping("/deleteAll")
    public void deleteAll(){
        habitService.deleteAll();
    }

}

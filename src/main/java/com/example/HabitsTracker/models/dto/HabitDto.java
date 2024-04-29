package com.example.HabitsTracker.models.dto;

import com.example.HabitsTracker.models.Colors;
import com.example.HabitsTracker.models.Icons;
import com.example.HabitsTracker.models.Period;
import lombok.Data;
import java.time.LocalDate;

@Data
public class HabitDto {

    private Long id;

    private String habitName;

    private String description;

    private int numReps;

    private int durationTime;

    private Period period;

    private boolean remind;

    private Colors color;

    private Icons icon;

    private LocalDate created;

    private boolean active;

}


package com.example.HabitsTracker.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "habit_done")
public class HabitDone {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private int numRepsDone;

    private LocalDateTime start;

    private LocalDateTime end;

    //@Type(type = "date")
    private LocalDate created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Habit habit;

}

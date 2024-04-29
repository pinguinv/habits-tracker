package com.example.HabitsTracker.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@Table( name = "habits")
public class Habit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String habitName;

    @Type(type = "text")
    private String description;

    private int numReps;

    private int durationTime;

    @Enumerated(EnumType.STRING)
    private Period period;

    private boolean remind;

    @Enumerated(EnumType.STRING)
    private Colors color;

    @Enumerated(EnumType.STRING)
    private Icons icon;

    //@Type(type = "date")
    private LocalDate created;

    private boolean active;


    // relacja między tabelami w bazie danych
    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HabitDone> habitDoneList;






}

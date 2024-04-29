package com.example.HabitsTracker.mappers;

import com.example.HabitsTracker.models.Habit;
import com.example.HabitsTracker.models.dto.HabitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HabitMapper {

    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    HabitDto map(Habit habit);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "habitDoneList", ignore = true)
    Habit map(HabitDto habitDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "habitDoneList", ignore = true)
    Habit map(HabitDto habitDto, @MappingTarget Habit target);

}

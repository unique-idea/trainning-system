package com.fptacademy.training.service.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReturnClassScheduleDtoOld {

    private String code;

    private String name;

    private int duration;

    private int currentClassDay;

    private String location;

    private String type;

    private ReturnUserDto trainer;

    private List<ReturnUserDto> admins = new ArrayList<>();

    private LocalDate date;

    private LocalTime startAt;

    private LocalTime finishAt;


    //additional information
    private String city;

    private String Fsu;

    private Long classId;

    private List<ReturnUserDto> trainers;

    private List<ReturnUnitDto> units;

    //additional information
}
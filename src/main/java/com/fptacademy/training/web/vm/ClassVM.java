package com.fptacademy.training.web.vm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ClassVM(
        String name,
        Long programId,
        LocalTime startAt,
        LocalTime finishAt,
        String detailLocation,
        Long fsuId,
        String contactPoint,
        Long attendeeId,
        int planned,
        int accepted,
        int actual,
        String others,
        List<Long> userIds,
        List<LocalDate> studyDates,
        String status //DRAFT or PLANNING or OPENNING
) {
}

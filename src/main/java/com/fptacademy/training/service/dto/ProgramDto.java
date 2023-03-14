package com.fptacademy.training.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
public class ProgramDto implements Serializable {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Instant createdAt;
    private Creator createdBy;
    private Instant lastModifiedAt;
    private Creator lastModifiedBy;
    private Integer durationInDays;
    private Integer durationInHours;
    private Boolean activated;
    @Getter
    @AllArgsConstructor
    public static class Creator {
        private Long id;
        private String name;
        private String code;
    }
}

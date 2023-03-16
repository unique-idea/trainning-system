package com.fptacademy.training.service.dto;

import com.fptacademy.training.domain.Assessment;
import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Material;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusDto {

  private Long id;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SyllabusListDto {

    private Long id;
    private String name;
    private String code;
    private Integer attendeeNumber;
    private SyllabusStatus status;
    private String technicalRequirement;
    private String courseObjective;
    private String trainingPrinciple;
    private Instant createdAt;
    private String createdBy;
    private Integer duration;
    private Double durationInHours;
    private List<OutputStandard> outputStandard = new ArrayList<>();
    private Float version;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SyllabusDetailDto {

    private Long id;
    private String name;
    private String createdBy;
    private Instant createdAt;
    private Instant lastModifiedAt;
    private String lastModifiedBy;
    private String code;
    private Float version;
    private Integer attendeeNumber;
    private String status;
    private Integer duration;
    private Double durationInHours;
    private String technicalRequirement;
    private String courseObjective;
    private String trainingPrinciple;
    private Level level;
    private Assessment assessment;
    private List<OutputStandard> outputStandard;
    private List<Session> sessions;
  }
}

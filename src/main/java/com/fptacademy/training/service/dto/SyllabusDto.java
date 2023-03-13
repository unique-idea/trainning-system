package com.fptacademy.training.service.dto;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.User;
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
    private List<OutputStandard> outputStandard = new ArrayList<>();
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
    private SyllabusStatus status;
    private Integer duration;
    private String technicalRequirement;
    private String courseObjective;
    private String trainingPrinciple;
    private Level level;
    private List<OutputStandard> outputStandard = new ArrayList<>();
    private List<SessionDto> sessions = new ArrayList<>();
  }

  public static class SessionDto {

    private Long id;

    private Integer index;

    private String name;

    private String status;

    private List<UnitDto> units = new ArrayList<>();
  }

  public static class UnitDto {

    private Long id;

    private String title;

    private String status;

    private String name;

    private Integer index;

    private List<LessonDto> lessons = new ArrayList<>();
  }

  public static class LessonDto {}

  public static class MaterialDto {}
}

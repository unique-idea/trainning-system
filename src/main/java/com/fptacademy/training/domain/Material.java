package com.fptacademy.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@ToString
@Table(name = "materials")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Material implements Serializable {

  private static final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String fileUrl;

  @CreatedBy
  @JsonIgnore
  @JoinColumn(name = "created_by")
  @ManyToOne
  private User createdBy;

  @CreatedDate
  @JsonIgnore
  private Instant createdAt;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "lesson_id")
  private Lesson lesson;
}

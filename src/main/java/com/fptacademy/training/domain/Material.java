package com.fptacademy.training.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@ToString
@Table(name = "materials")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Material implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    private String name;
    private String fileUrl;
    @CreatedBy
    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;
    @CreatedDate
    private Instant createdAt;
}

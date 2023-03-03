package com.FaTrainingSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback implements Serializable {
    private static final long serialVersionUID = 7769530806131885529L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fb_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "class_id", nullable = false)
    private Long classId;

    @NotNull
    @Column(name = "program_id", nullable = false)
    private Long programId;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "uploadFile")
    private byte[] uploadFile;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

}
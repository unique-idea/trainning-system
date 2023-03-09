package com.fptacademy.training.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@Table(name = "lessons")
@Entity
public class Lesson implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
    @ManyToOne
    @JoinColumn(name = "output_standard_id")
    private OutputStandard outputStandard;
    @ManyToOne
    @JoinColumn(name = "format_type_id")
    private FormatType formatType;
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    @Size(max = 100)
    @Column(length = 100)
    private String name;
    @Size(max = 45)
    @Column(length = 45)
    private String status;
    private Integer duration;
    @OneToMany(mappedBy = "lesson")
    private List<Material> materials;
}

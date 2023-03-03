package com.FaTrainingSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_permission")
public class UserPermission implements Serializable {
    private static final long serialVersionUID = -4043712315820643843L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "up_id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @Column(name = "role_name", length = 30)
    private String roleName;

    @Size(max = 30)
    @Column(name = "object", length = 30)
    private String object;

    @Size(max = 30)
    @Column(name = "permission", length = 30)
    private String permission;

}
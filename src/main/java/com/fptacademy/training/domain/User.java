package com.fptacademy.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fptacademy.training.domain.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 20)
    @Column(length = 20, unique = true, nullable = false)
    private String code;

    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String fullName;

    @Size(max = 100)
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Size(max = 100)
    @Column(length = 100, nullable = false)
    @JsonIgnore
    private String password;

    @Past
    private LocalDate birthday;

    private Boolean gender;

    @Column(nullable = false)
    private Boolean activated;

    private String avatarUrl;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}

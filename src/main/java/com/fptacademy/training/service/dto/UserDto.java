package com.fptacademy.training.service.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String fullName;

    private String email;

    private String birthday;

    private Boolean gender;

    private Boolean activated;

    private String level;

    private String role;

    private String avatarUrl;

    private String code;
}

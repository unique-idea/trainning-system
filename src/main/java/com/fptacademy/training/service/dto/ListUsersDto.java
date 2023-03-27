package com.fptacademy.training.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListUsersDto {
    private Integer totalPages;

    private Long totalElements;

    private Integer size;

    private Integer page;

    private List<UserDto> users;
}

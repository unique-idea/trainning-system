package com.fptacademy.training.web.vm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record ProgramVM(
        @NotBlank(message = "Program name is required")
        String name,
        @NotEmpty(message = "Program must have at least one syllabus")
        List<Long> syllabusIds) {
}

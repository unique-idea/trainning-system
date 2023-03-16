package com.fptacademy.training.web.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
public record RoleVM(
        @Schema(example = "Class Admin")
        String name,
        @Schema(defaultValue = "[\"Syllabus_View\", \"Program_Create\", \"Class_Modify\", \"Material_FullAccess\", \"User_AccessDenied\"]")
        List<String> permissions
) {
}

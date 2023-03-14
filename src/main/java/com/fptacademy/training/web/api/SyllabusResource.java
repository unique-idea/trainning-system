package com.fptacademy.training.web.api;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.OutputStandard;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public interface SyllabusResource {
    @Operation(
            summary = "Add new level",
            description = "JSON description: \n" +
                    "{\n" +
                    "  \"name\": \"enter your level name\"\n" +
                    "}",
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Level.class), examples = @ExampleObject(
                            name = "Example", value = "[{\"id\": 1, \"name\": \"Fresher\"}" +
                            ", {\"id\": 2, \"name\": \"Junior\"}" +
                            ", {\"id\": 3, \"name\": \"Senior\"}" +
                            ", {\"id\": 4, \"name\": \"Manager\"}" +
                            ", {\"id\": 5, \"name\": \"Super\"}]")
                    )),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Not found", responseCode = "404"),
                    @ApiResponse(description = "This name is already exists", responseCode = "500", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Level.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"H2SO4\"}")))
            }
    )
    @PostMapping("/outputStandards")
    ResponseEntity<OutputStandard> createOutputStandard(@RequestBody OutputStandard OutputStandardDTO);

    @PutMapping(value = "/outputStandards/{id}")
    public ResponseEntity<OutputStandard> updateOutputStandard(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OutputStandard OutputStandardDTO
    );

    @GetMapping("/outputStandards")
    public ResponseEntity<List<OutputStandard>> getAllOutputStandards();

    @GetMapping("/outputStandards/{id}")
    public ResponseEntity<OutputStandard> getOutputStandard(@PathVariable Long id);

    @GetMapping("/levels")
    public ResponseEntity<List<Level>> getAllLevel();

    @GetMapping("/levels/{id}")
    public ResponseEntity<Level> getLevel(@PathVariable Long id);

    @GetMapping("/levels/{name}")
    public ResponseEntity<Level> getLevel(@PathVariable String name);


}

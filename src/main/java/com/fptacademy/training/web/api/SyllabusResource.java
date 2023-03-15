package com.fptacademy.training.web.api;

import com.fptacademy.training.domain.OutputStandard;
import java.util.List;

import com.fptacademy.training.service.dto.SyllabusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface SyllabusResource {
  @PostMapping("/OutputStandards")
  ResponseEntity<OutputStandard> createOutputStandard(@RequestBody OutputStandard OutputStandardDTO);

  @PutMapping(value = "/OutputStandards/{id}")
  public ResponseEntity<OutputStandard> updateOutputStandard(
          @PathVariable(value = "id", required = false) final Long id,
          @RequestBody OutputStandard OutputStandardDTO
  );

  @GetMapping("/OutputStandards")
  public ResponseEntity<List<OutputStandard>> getAllOutputStandards();

  @GetMapping("/OutputStandards/{id}")
  public ResponseEntity<OutputStandard> getOutputStandard(@PathVariable Long id);
}


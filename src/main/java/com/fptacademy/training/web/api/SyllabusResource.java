package com.fptacademy.training.web.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fptacademy.training.domain.OutputStandard;

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

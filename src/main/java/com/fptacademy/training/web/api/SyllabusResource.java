package com.fptacademy.training.web.api;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.OutputStandard;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface SyllabusResource {
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

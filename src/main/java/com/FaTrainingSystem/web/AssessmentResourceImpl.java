package com.FaTrainingSystem.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assessment")
public class AssessmentResourceImpl {

  @GetMapping
  public ResponseEntity<?> getAssessment() {
    return ResponseEntity.ok("Assessment");
  }
}

package com.fptacademy.training.web.api;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.domain.ClassDetail;
import com.fptacademy.training.service.dto.ClassDetailDto;
import com.fptacademy.training.service.dto.ClassDto;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.web.vm.ClassVM;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RequestMapping("/api/class")
public interface ClassResource {

    @GetMapping("/{class_id}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable Long class_id);

    @GetMapping("/details/{class_id}")
    public ResponseEntity<ClassDetailDto> getDetailsByClassId(@PathVariable Long class_id);

    @GetMapping
    public ResponseEntity<List<ClassDto>> filterClass(
            @RequestParam(value = "q", required = false) List<String> keywords,
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "location", required = false) List<String> cities,
            @RequestParam(name = "classTime", required = false) List<String> classTimes,
            @RequestParam(name = "status", required = false) List<String> statuses,
            @RequestParam(name = "attendee", required = false) List<String> attendeeTypes,
            @RequestParam(name = "fsu", required = false) String fsu,
            @RequestParam(name = "trainer", required = false) String trainerCode,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    );
    @DeleteMapping ("/{id}")
    public ResponseEntity<String> delClass(@PathVariable Long id);

    @PutMapping ("/deactivate/{id}")
    public ResponseEntity<String> deactivateClass(@PathVariable Long id);

    @PutMapping ("/activate/{id}")
    public ResponseEntity<String> activateClass(@PathVariable Long id);

    @PostMapping
    public ResponseEntity<ClassDetailDto> createClass(@RequestBody ClassVM classVM);

    @GetMapping("/trainer")
    public ResponseEntity<List<UserDto>> getAllTrainer();

    @GetMapping("/class_admin")
    public ResponseEntity<List<UserDto>> getAllClassAdmin();

}

package com.fptacademy.training.web.api;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.service.dto.ClassDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RequestMapping("/api/classes")

public interface ClassResource {

    @GetMapping("/{class_id}")
    public Optional<ClassDto> getClassById(@PathVariable Long class_id);

    @GetMapping("/all")
    public List<ClassDto> getAllClass();

    @GetMapping("/delete/{id}")
    public void delClass(@PathVariable Long id);

}

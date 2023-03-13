package com.fptacademy.training.web;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.repository.ClassRepository;
import com.fptacademy.training.service.dto.ClassDto;
import com.fptacademy.training.service.mapper.ClassMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClassResourceImpl {

    private final ClassRepository repository;
    private final ClassMapper classMapper;

    public ClassResourceImpl(ClassRepository repository, ClassMapper classMapper) {
        this.repository = repository;
        this.classMapper = classMapper;
    }

    @GetMapping("/classes/{class_id}")
    public Optional<ClassDto> getClassById(@PathVariable Long class_id){
        Optional<Class> classes = repository.findById(class_id);
        return classes.map(classMapper::toDto);
    }

    @GetMapping("/classes/all")
    public List<ClassDto> getAllClass(){
        List<Class> classes = repository.findAll();
        return classes.stream().map(classMapper::toDto).toList();
    }
}

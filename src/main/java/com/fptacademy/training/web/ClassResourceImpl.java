package com.fptacademy.training.web;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.repository.ClassDetailRepository;
import com.fptacademy.training.repository.ClassRepository;
import com.fptacademy.training.service.dto.ClassDto;
import com.fptacademy.training.service.mapper.ClassMapper;
import com.fptacademy.training.web.api.ClassResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ClassResourceImpl implements ClassResource {

    private final ClassRepository repository;
    private final ClassMapper classMapper;
    private final ClassDetailRepository detailRepository ;

    @Override
    public Optional<ClassDto> getClassById(@PathVariable Long class_id){
        Optional<Class> classes = repository.findById(class_id);
        return classes.map(classMapper::toDto);
    }

    @Override
    public List<ClassDto> getAllClass(){
        List<Class> classes = repository.findAll();
        return classes.stream().map(classMapper::toDto).toList();
    }

    @Override
    public void delClass(Long id) {
        Optional<Class> classes = repository.findById(id);
        detailRepository.updateStatusById("Inactive",classes.get().getClassDetail().getId());
    }


    @Override
    public ClassDto duplicateClass(@PathVariable Long id) {
        Optional<Class> classesOp = repository.findById(id);
        Date date = new Date();
        Class classes = classesOp.get();

        Class duplicateClass = new Class();
        duplicateClass.setName("Copy of " + classes.getName());
        duplicateClass.setCreatedAt(classes.getCreatedAt());
        duplicateClass.setCode(classes.getCode() + " Version: " + date);
        duplicateClass.setDuration(classes.getDuration());
        duplicateClass.setCreatedBy(classes.getCreatedBy());
//        duplicateClass.

        //        repository.save(duplicateClass);
        return classMapper.toDto(duplicateClass);
    }


}

package com.fptacademy.training.web;

import java.util.List;
import com.fptacademy.training.domain.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fptacademy.training.repository.ClassRepository;
import com.fptacademy.training.web.api.ClassResource;

@RestController
public class ClassResourceImpl implements ClassResource {
    @Autowired
    private ClassRepository classRepository;

    @GetMapping("/getAll")
    List<Class>getAll(){
        return classRepository.findAll();
    }

}

package com.fptacademy.training.service;

import com.fptacademy.training.domain.FormatType;
import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.repository.FormatTypeRepository;
import com.fptacademy.training.service.dto.LessonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormatTypeService {
    @Autowired
    public FormatTypeRepository formatTypeRepository;

    public List<FormatType> getAll(){
        return formatTypeRepository.findAll();
    }

    public Optional<FormatType> getFormatTypeByID(Long id){

        return formatTypeRepository.findById(id);
    }

    public List<FormatType> getFormatTypeByName(String name){

        return formatTypeRepository.findFormatTypeByNameContains(name);
    }

}

package com.fptacademy.training.service;

import com.fptacademy.training.domain.Assessment;
import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.repository.AssessmentRepository;
import com.fptacademy.training.service.dto.LessonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService {
    @Autowired
    private AssessmentRepository assessmentRepository;

    public List<Assessment> getAll(){
        return assessmentRepository.findAll();
    }

    public Optional<Assessment> getAssessmentByID(Long id){

        return assessmentRepository.findById(id);
    }

}

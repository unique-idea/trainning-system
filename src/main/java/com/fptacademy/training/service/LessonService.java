package com.fptacademy.training.service;

import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.repository.LessonRepository;
import com.fptacademy.training.service.dto.LessonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<LessonDTO> getAll(){
        List<Lesson> list = lessonRepository.findAll();
        List<LessonDTO> listDTO = new ArrayList<>();
        for(Lesson op : list){
            listDTO.add(modelMapper.map(op, LessonDTO.class));
        }
        return listDTO;
    }

    public Optional<LessonDTO> getLessonByID(Long id){

        return lessonRepository.findById(id).map(l->modelMapper.map(l,LessonDTO.class));
    }

    public List<LessonDTO> getLessonByName(String name){
        List<Lesson> list = lessonRepository.findByNameContains(name);
        List<LessonDTO> listDTO = new ArrayList<>();
        for(Lesson op : list){
            listDTO.add(modelMapper.map(op, LessonDTO.class));
        }
        return listDTO;
    }
}

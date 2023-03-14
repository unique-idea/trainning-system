package com.fptacademy.training.service;

import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Unit;
import com.fptacademy.training.repository.LevelRepository;
import com.fptacademy.training.repository.UnitRepository;
import com.fptacademy.training.service.dto.LessonDTO;
import com.fptacademy.training.service.dto.UnitDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UnitDTO> getAll() {
        List<Unit> list = unitRepository.findAll();
        List<UnitDTO> listDTO = new ArrayList<>();
        for (Unit op : list) {
            listDTO.add(modelMapper.map(op, UnitDTO.class));
        }
        return listDTO;
    }

    public Optional<UnitDTO> getUnitByID(Long id) {
        return unitRepository.findById(id).map(l -> modelMapper.map(l, UnitDTO.class));
    }

    public List<UnitDTO> getUnitByName(String name) {
        List<Unit> list = unitRepository.findByNameContains(name);
        List<UnitDTO> listDTO = new ArrayList<>();
        for (Unit op : list) {
            listDTO.add(modelMapper.map(op, UnitDTO.class));
        }
        return listDTO;
    }

    public Unit save(Unit unit) {
        return null;
    }

    public void delete(Long id) {

    }
}

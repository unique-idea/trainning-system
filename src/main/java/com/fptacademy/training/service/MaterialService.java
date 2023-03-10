package com.fptacademy.training.service;

import com.fptacademy.training.domain.Material;
import com.fptacademy.training.domain.Unit;
import com.fptacademy.training.repository.MaterialRepository;
import com.fptacademy.training.repository.UnitRepository;
import com.fptacademy.training.service.dto.MaterialDTO;
import com.fptacademy.training.service.dto.UnitDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<MaterialDTO> getAll() {
        List<Material> list = materialRepository.findAll();
        List<MaterialDTO> listDTO = new ArrayList<>();
        for (Material op : list) {
            listDTO.add(modelMapper.map(op, MaterialDTO.class));
        }
        return listDTO;
    }

    public Optional<MaterialDTO> getMaterialByID(Long id) {
        return materialRepository.findById(id).map(l -> modelMapper.map(l, MaterialDTO.class));
    }

    public List<MaterialDTO> getMaterialByName(String name) {
        List<Material> list = materialRepository.findByNameContains(name);
        List<MaterialDTO> listDTO = new ArrayList<>();
        for (Material op : list) {
            listDTO.add(modelMapper.map(op, MaterialDTO.class));
        }
        return listDTO;
    }

}

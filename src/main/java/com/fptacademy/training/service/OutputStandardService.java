package com.fptacademy.training.service;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.repository.OutputStandardRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class OutputStandardService {
    private final ModelMapper modelMapper;
    private final OutputStandardRepository outputStandardRepository;

    @Autowired
    public OutputStandardService(OutputStandardRepository outputStandardRepository, ModelMapper modelMapper) {
        this.outputStandardRepository = outputStandardRepository;
        this.modelMapper = modelMapper;
    }

    public OutputStandard save(OutputStandard outputStandard) {
        Optional<OutputStandard> outputStandardOptional = outputStandardRepository.findByName(outputStandard.getName());
        if(outputStandardOptional.isPresent()) {
            throw new IllegalStateException("The name: "+ outputStandard.getName()+ " is already had.");
        }

        return outputStandardRepository.save(outputStandard);
    }

    public Optional<OutputStandard> update(Long id, OutputStandard outputStandard) {
        if (id == null) {
            throw new ResourceBadRequestException("id null");
        }
        if (!outputStandardRepository.existsById(id)) {
            throw new ResourceBadRequestException("Entity not found id.");
        }
        if (outputStandard.getName().length() == 0 || outputStandard.getName().length() > 10) {
            throw new IllegalStateException("Error: The length of name must in [1 - 10].");
        }
        outputStandard.setId(id);

        return findOne(id).map(ops -> {
                    modelMapper.map(outputStandard, ops);
                    return ops;
                })
                .map(outputStandardRepository::save);
    }

    @Transactional(readOnly = true)
    public List<OutputStandard> findAll() {
        return outputStandardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<OutputStandard> findOne(Long id) {
        return outputStandardRepository.findById(id);
    }

    public List<OutputStandard> getOutputStandardByName(String name) {
        return outputStandardRepository.findByNameContains(name);
    }

    public void delete(Long id) {
        outputStandardRepository.deleteById(id);
    }
}

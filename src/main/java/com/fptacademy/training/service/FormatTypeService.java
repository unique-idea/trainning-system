package com.fptacademy.training.service;

import com.fptacademy.training.domain.FormatType;
import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.repository.FormatTypeRepository;
import com.fptacademy.training.service.dto.LessonDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FormatTypeService {
    private final FormatTypeRepository formatTypeRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<FormatType> getAll() {
        return formatTypeRepository.findAll();
    }

    @Transactional
    public Optional<FormatType> getOne(Long id) {
        return formatTypeRepository.findById(id);
    }

    public void save(FormatType formatType) {
        formatTypeRepository.save(formatType);
    }

    public Optional<FormatType> update(FormatType result) {
        return formatTypeRepository
          .findById(result.getId())
          .map(ops -> {
            modelMapper.map(result, ops);
            return ops;
          })
          .map(formatTypeRepository::save);
      }

    public void delete(Long id) {
        formatTypeRepository.deleteById(id);
    }
}

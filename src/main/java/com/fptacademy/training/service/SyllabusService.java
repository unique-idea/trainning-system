package com.fptacademy.training.service;

import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusListDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SyllabusService {

  private final SyllabusRepository syllabusRepository;
  private final ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public Page<SyllabusListDto> findAll(Pageable pageable) {
    return syllabusRepository.findAll(pageable).map(s -> modelMapper.map(s, SyllabusListDto.class));
  }
}

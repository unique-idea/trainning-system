package com.fptacademy.training.service;

import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusListDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SyllabusService {

  private final SyllabusRepository syllabusRepository;
  private final ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public Page<SyllabusListDto> findAll(Specification<Syllabus> spec, Pageable pageable) {
    TypeMap<Syllabus, SyllabusListDto> typeMap = modelMapper.getTypeMap(Syllabus.class, SyllabusListDto.class) == null
      ? modelMapper
        .createTypeMap(Syllabus.class, SyllabusListDto.class)
        .addMappings(mapper -> mapper.map(src -> src.getCreatedBy().getCode(), SyllabusListDto::setCreatedBy))
        .addMappings(mapper ->
          mapper
            .using(
              (Converter<List<Session>, List<OutputStandard>>) ctx ->
                ctx
                  .getSource()
                  .stream()
                  .flatMap(session -> session.getUnits().stream())
                  .flatMap(unit -> unit.getLessons().stream())
                  .map(Lesson::getOutputStandard)
                  .distinct()
                  .toList()
            )
            .map(Syllabus::getSessions, SyllabusListDto::setOutputStandard)
        )
      : modelMapper.getTypeMap(Syllabus.class, SyllabusListDto.class);

    return syllabusRepository.findAll(spec, pageable).map(s -> modelMapper.map(s, SyllabusListDto.class));
  }

  public Syllabus save(Syllabus syllabus) {
    return syllabusRepository.save(syllabus);
  }

  public Optional<Syllabus> update(Syllabus syllabus) {
    return syllabusRepository
      .findById(syllabus.getId())
      .map(ops -> {
        modelMapper.map(syllabus, ops);
        return ops;
      })
      .map(syllabusRepository::save);
  }

  @Transactional(readOnly = true)
  public Optional<Syllabus> findOne(Long id) {
    return syllabusRepository.findById(id);
  }

  public void delete(Long id) {
    syllabusRepository.deleteById(id);
  }
}

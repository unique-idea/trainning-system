package com.fptacademy.training.service;

import com.fptacademy.training.domain.Assessment;
import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.Material;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.domain.Unit;
import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.SyllabusDto;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusDetailDto;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusListDto;
import com.fptacademy.training.service.mapper.SyllabusMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
  private final SyllabusMapper syllabusMapper;

  @Transactional(readOnly = true)
  public Page<SyllabusListDto> findAll(Specification<Syllabus> spec, Pageable pageable) {
    TypeMap<Syllabus, SyllabusListDto> typeMap = modelMapper.getTypeMap(Syllabus.class, SyllabusListDto.class);
    if (typeMap == null) {
      typeMap =
        modelMapper
          .createTypeMap(Syllabus.class, SyllabusListDto.class)
          .addMappings(mapper -> {
            mapper.map(src -> src.getCreatedBy().getCode(), SyllabusListDto::setCreatedBy);
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
              .map(Syllabus::getSessions, SyllabusListDto::setOutputStandard);
            // mapper.using((Converter<List<Session>, Integer>) ctx -> ctx.getSource().size()).map(Syllabus::getSessions, SyllabusListDto::setDuration);
            // mapper.skip(SyllabusListDto::setStatus);
            // mapper.when(ctx -> Objects.nonNull(ctx.getSource())).map(Syllabus::getStatus, SyllabusListDto::setStatus);
            // new PropertyMap<Syllabus, SyllabusListDto>() {
            //   @Override
            //   protected void configure() {
            //     skip(destination.getStatus());
            //   }
            // };
          });
    }
    return syllabusRepository.findAll(spec, pageable).map(s -> modelMapper.map(s, SyllabusListDto.class));
  }

  public SyllabusDetailDto save(SyllabusDetailDto syllabusDetailDto) {
    ModelMapper map = new ModelMapper();
    map
      .createTypeMap(SyllabusDetailDto.class, Syllabus.class)
      .addMappings(mapper -> {
        mapper.skip(Syllabus::setId);
        mapper.<SyllabusStatus>map(src -> SyllabusStatus.DRAFT, Syllabus::setStatus);
        mapper.using((Converter<List<Session>, Integer>) ctx -> ctx.getSource().size()).map(SyllabusDetailDto::getSessions, Syllabus::setDuration);
      });
    map.createTypeMap(Assessment.class, Assessment.class).addMappings(mapper -> mapper.skip(Assessment::setId));
    map.createTypeMap(Session.class, Session.class).addMappings(mapper -> mapper.skip(Session::setId));
    map
      .createTypeMap(Unit.class, Unit.class)
      .addMappings(mapper -> {
        mapper.skip(Unit::setId);
        mapper
          .using((Converter<List<Lesson>, Double>) ctx -> ctx.getSource().stream().mapToDouble(Lesson::getDuration).sum() / 60)
          .map(Unit::getLessons, Unit::setTotalDurationLesson);
      });
    map.createTypeMap(Lesson.class, Lesson.class).addMappings(mapper -> mapper.skip(Lesson::setId));
    map.createTypeMap(Material.class, Material.class).addMappings(mapper -> mapper.skip(Material::setId));
    return modelMapper.map(syllabusRepository.save(map.map(syllabusDetailDto, Syllabus.class)), SyllabusDetailDto.class);
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
  public Optional<SyllabusDetailDto> findOne(Long id) {
    TypeMap<Syllabus, SyllabusDetailDto> typeMap = modelMapper.getTypeMap(Syllabus.class, SyllabusDetailDto.class);
    if (typeMap == null) {
      typeMap =
        modelMapper
          .createTypeMap(Syllabus.class, SyllabusDetailDto.class)
          .addMappings(mapper -> {
            mapper.map(src -> src.getLastModifiedBy().getFullName(), SyllabusDetailDto::setLastModifiedBy);
            mapper.map(src -> src.getCreatedBy().getFullName(), SyllabusDetailDto::setCreatedBy);
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
              .map(Syllabus::getSessions, SyllabusDetailDto::setOutputStandard);
            // mapper
            //   .using((Converter<List<Session>, Integer>) ctx -> ctx.getSource().size())
            //   .map(Syllabus::getSessions, SyllabusDetailDto::setDuration);
            mapper
              .using(
                (Converter<List<Session>, Double>) ctx ->
                  ctx
                    .getSource()
                    .stream()
                    .flatMap(session -> session.getUnits().stream())
                    .flatMap(unit -> unit.getLessons().stream())
                    .mapToDouble(Lesson::getDuration)
                    .sum() /
                  60
              )
              .map(Syllabus::getSessions, SyllabusDetailDto::setDurationInHours);
            mapper
              .using(
                (Converter<List<Session>, List<Session>>) ctx ->
                  ctx
                    .getSource()
                    .stream()
                    .sorted(Comparator.comparing(Session::getIndex))
                    .map(session -> {
                      session.setUnits(
                        session.getUnits().stream().sorted((u1, u2) -> u1.getIndex().compareTo(u2.getIndex())).collect(Collectors.toList())
                      );
                      return session;
                    })
                    .collect(Collectors.toList())
              )
              .map(Syllabus::getSessions, SyllabusDetailDto::setSessions);
          });
    }
    return syllabusRepository.findById(id).map(syl -> modelMapper.map(syl, SyllabusDetailDto.class));
  }

  public void delete(Syllabus syllabus) {
    syllabusRepository.save(syllabus);
  }

  public List<SyllabusDto.SyllabusListDto> findSyllabusesByName(String name) {
    List<Syllabus> syllabuses = syllabusRepository.findByNameContainsIgnoreCase(name);
    List<SyllabusDto.SyllabusListDto> syllabusesDto = syllabusMapper.toDtos(syllabuses);
    return syllabusesDto;
  }
}

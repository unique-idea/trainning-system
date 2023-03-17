package com.fptacademy.training.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.SyllabusDto;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusDetailDto;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusListDto;
import com.fptacademy.training.service.mapper.SyllabusMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final ModelMapper modelMapper;
    private final SyllabusMapper syllabusMapper;
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
                .addMappings(mapper ->
                        mapper.using((Converter<List<Session>, Integer>) ctx -> ctx.getSource().size()).map(Syllabus::getSessions, SyllabusListDto::setDuration)
                )
                // .addMappings(
                //   new PropertyMap<Syllabus, SyllabusListDto>() {
                //     @Override
                //     protected void configure() {
                //       skip(destination.getStatus());
                //     }
                //   }
                // )
                // .addMappings(mapper -> {
                //   mapper.map(src -> null, SyllabusListDto::setStatus);
                // })
                // .addMappings(mapper -> {
                //   mapper.skip(SyllabusListDto::setStatus);
                //   mapper.when(ctx -> Objects.nonNull(ctx.getSource())).map(Syllabus::getStatus, SyllabusListDto::setStatus);
                // })
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
    public Optional<SyllabusDetailDto> findOne(Long id) {
        TypeMap<Syllabus, SyllabusDetailDto> typeMap = modelMapper.getTypeMap(Syllabus.class, SyllabusDetailDto.class) == null
                ? modelMapper
                .createTypeMap(Syllabus.class, SyllabusDetailDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getCreatedBy().getFullName(), SyllabusDetailDto::setCreatedBy))
                .addMappings(mapper -> mapper.map(src -> src.getLastModifiedBy().getFullName(), SyllabusDetailDto::setLastModifiedBy))
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
                                .map(Syllabus::getSessions, SyllabusDetailDto::setOutputStandard)
                )
                .addMappings(mapper ->
                        mapper.using((Converter<List<Session>, Integer>) ctx -> ctx.getSource().size()).map(Syllabus::getSessions, SyllabusDetailDto::setDuration)
                )
                .addMappings(mapper ->
                        mapper
                                .using(
                                        (Converter<List<Session>, Double>) ctx ->
                                                ctx
                                                        .getSource()
                                                        .stream()
                                                        .flatMap(session -> session.getUnits().stream())
                                                        .flatMap(unit -> unit.getLessons().stream())
                                                        .mapToDouble(Lesson::getDuration)
                                                        .sum()
                                )
                                .map(Syllabus::getSessions, SyllabusDetailDto::setTotalDurationUnit)
                )
                : modelMapper.getTypeMap(Syllabus.class, SyllabusDetailDto.class);

        return syllabusRepository.findById(id).map(syl -> modelMapper.map(syl, SyllabusDetailDto.class));
    }

    public void delete(Syllabus syllabus) {
        syllabusRepository.save(syllabus);
    }
    public List<SyllabusDto.SyllabusListDto>findSyllabusesByName(String name){
        List<Syllabus> syllabuses = syllabusRepository.findByNameContainsIgnoreCase(name);
        List<SyllabusDto.SyllabusListDto> syllabusesDto=syllabusMapper.toDtos(syllabuses);
        return syllabusesDto;
    }
}

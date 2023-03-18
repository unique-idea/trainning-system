package com.fptacademy.training.service;

import com.fptacademy.training.domain.Assessment;
import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Material;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.domain.Unit;
import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.DeliveryRepository;
import com.fptacademy.training.repository.FormatTypeRepository;
import com.fptacademy.training.repository.LessonRepository;
import com.fptacademy.training.repository.LevelRepository;
import com.fptacademy.training.repository.MaterialRepository;
import com.fptacademy.training.repository.OutputStandardRepository;
import com.fptacademy.training.repository.SessionRepository;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.repository.UnitRepository;
import com.fptacademy.training.service.dto.SyllabusDto;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusDetailDto;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusListDto;
import com.fptacademy.training.service.mapper.SyllabusMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional
public class SyllabusService {

  private final SyllabusRepository syllabusRepository;
  private final ModelMapper modelMapper;
  private final SyllabusMapper syllabusMapper;
  private final LevelRepository levelRepository;
  private final FormatTypeRepository formatTypeRepository;
  private final DeliveryRepository deliveryRepository;
  private final OutputStandardRepository outputStandardRepository;
  private final SessionRepository sessionRepository;
  private final UnitRepository unitRepository;
  private final LessonRepository lessonRepository;
  private final MaterialRepository materialRepository;

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
        mapper.map(src -> 1.0, Syllabus::setVersion);
        mapper.map(src -> Long.toString(UUID.randomUUID().getMostSignificantBits() & 0xffffff, 36).toUpperCase(), Syllabus::setCode);
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

  public Optional<SyllabusDetailDto> update(SyllabusDetailDto syllabusDto) {
    TypeMap<SyllabusDetailDto, Syllabus> typeMap = modelMapper.getTypeMap(SyllabusDetailDto.class, Syllabus.class);
    if (typeMap == null) {
      typeMap =
        modelMapper
          .createTypeMap(SyllabusDetailDto.class, Syllabus.class)
          .addMappings(mapper -> {
            mapper.skip(Syllabus::setSessions);
            mapper.skip(Syllabus::setStatus);
            mapper.skip(Syllabus::setCreatedAt);
            mapper.skip(Syllabus::setLastModifiedAt);
            mapper.skip(Syllabus::setCode);
            mapper.skip(Syllabus::setVersion);
            mapper
              .using((Converter<List<Session>, Integer>) ctx -> ctx.getSource().size())
              .map(SyllabusDetailDto::getSessions, Syllabus::setDuration);
          });
    }
    return syllabusRepository
      .findById(syllabusDto.getId())
      .map(syl -> {
        modelMapper.map(syllabusDto, syl);
        syl.setVersion(syl.getVersion() + 0.1F);
        syl.getSessions().clear();
        syl.getSessions().addAll(modelMapper.map(syllabusDto.getSessions(), new TypeToken<List<Session>>() {}.getType()));
        syl
          .getSessions()
          .forEach(session -> {
            session.setSyllabus(syl);
            session
              .getUnits()
              .forEach(unit -> {
                unit.setSession(session);
                unit.setTotalDurationLesson(unit.getLessons().stream().mapToDouble(Lesson::getDuration).sum() / 60);
                unit
                  .getLessons()
                  .forEach(lesson -> {
                    lesson.setUnit(unit);
                    lesson.getMaterials().forEach(material -> material.setLesson(lesson));
                  });
              });
          });
        return syl;
      })
      .map(syllabusRepository::save)
      .map(syl -> modelMapper.map(syl, SyllabusDetailDto.class));
  }

  @Transactional(readOnly = true)
  public Optional<SyllabusDetailDto> findOne(Long id) {
    ModelMapper map = new ModelMapper();
    map
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
                .sorted(Comparator.comparing(session -> session.getIndex(), Comparator.nullsLast(Integer::compareTo)))
                .peek(session ->
                  session.setUnits(
                    session
                      .getUnits()
                      .stream()
                      .sorted(Comparator.comparing(unit -> unit.getIndex(), Comparator.nullsLast(Integer::compareTo)))
                      .peek(unit ->
                        unit.setLessons(
                          unit
                            .getLessons()
                            .stream()
                            .sorted(Comparator.comparing(lession -> lession.getIndex(), Comparator.nullsLast(Integer::compareTo)))
                            .toList()
                        )
                      )
                      .toList()
                  )
                )
                .toList()
          )
          .map(Syllabus::getSessions, SyllabusDetailDto::setSessions);
      });

    return syllabusRepository.findById(id).map(syl -> map.map(syl, SyllabusDetailDto.class));
  }

  public void delete(Syllabus syllabus) {
    syllabusRepository.save(syllabus);
  }

  public List<?> importExcel(MultipartFile file, String[] scanning, String handle) {
    List<Syllabus> syllabuses = new ArrayList<>();
    try {
      Workbook workbook = new XSSFWorkbook(file.getInputStream());
      Sheet syllabusSheet = workbook.getSheet("syllabus");
      Sheet sessionSheet = workbook.getSheet("session");
      Sheet assessmentSheet = workbook.getSheet("assessment");
      Sheet unitSheet = workbook.getSheet("unit");
      Sheet lessonSheet = workbook.getSheet("lesson");
      Sheet materialSheet = workbook.getSheet("material");
      syllabusSheet.removeRow(syllabusSheet.getRow(0));
      sessionSheet.removeRow(sessionSheet.getRow(0));
      assessmentSheet.removeRow(assessmentSheet.getRow(0));
      unitSheet.removeRow(unitSheet.getRow(0));
      lessonSheet.removeRow(lessonSheet.getRow(0));
      materialSheet.removeRow(materialSheet.getRow(0));

      syllabusSheet.forEach(row -> {
        syllabuses.add(
          Syllabus
            .builder()
            .id(row.getCell(0) != null && row.getCell(0).getCellType() == CellType.NUMERIC ? (long) row.getCell(0).getNumericCellValue() : null)
            .code(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null)
            .name(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null)
            .attendeeNumber(
              row.getCell(3) != null && row.getCell(3).getCellType() == CellType.NUMERIC ? (int) row.getCell(3).getNumericCellValue() : null
            )
            .status(SyllabusStatus.DRAFT)
            // .duration(null)
            .version(1.0F)
            .courseObjective(row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null)
            .technicalRequirement(row.getCell(5) != null ? row.getCell(5).getStringCellValue() : null)
            .trainingPrinciple(row.getCell(6) != null ? row.getCell(6).getStringCellValue() : null)
            .level(
              row.getCell(7) != null && row.getCell(7).getCellType() == CellType.NUMERIC
                ? levelRepository
                  .findById((long) row.getCell(7).getNumericCellValue())
                  .orElseThrow(() -> new ResourceNotFoundException("Level" + new CellReference(row.getCell(7)).formatAsString()))
                : null
            )
            // .sessions(null)
            // .assessment(null)
            .build()
        );
      });
      workbook.close();
    } catch (IOException e) {
      throw new ResourceBadRequestException("dsadasd", e);
    } finally {}

    return syllabuses;
  }

  public List<SyllabusDto.SyllabusListDto> findSyllabusesByName(String name) {
    List<Syllabus> syllabuses = syllabusRepository.findByNameContainsIgnoreCase(name);
    List<SyllabusDto.SyllabusListDto> syllabusesDto = syllabusMapper.toDtos(syllabuses);
    return syllabusesDto;
  }
}

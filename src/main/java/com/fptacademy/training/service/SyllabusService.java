package com.fptacademy.training.service;

import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.domain.Unit;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusListDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
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
    // Provider<List<OutputStandard>> outputStandardProvider = new AbstractProvider<List<OutputStandard>>() {
    //   @Override
    //   public List<OutputStandard> get() {
    //     return new ArrayList<>();
    //   }
    // };

    // PropertyMap<Syllabus, SyllabusListDto> orderMap = new PropertyMap<Syllabus, SyllabusListDto>() {
    //   protected void configure() {
    //     source.setName(destination.getSessions().get(0).getName());
    //   }
    // };

    // Tạo một đối tượng ModelMapper
    ModelMapper modelMapper1 = new ModelMapper();
    // modelMapper1.addMappings(orderMap);

    // Tạo một TypeMap để ánh xạ từ Syllabus sang SyllabusListDto
    TypeMap<Syllabus, SyllabusListDto> syllabusTypeMap = modelMapper1.createTypeMap(Syllabus.class, SyllabusListDto.class);

    // Thiết lập ánh xạ cho các thuộc tính không tương đương nhau giữa Syllabus và SyllabusListDto
    // SyllabusListDto o = modelMapper1.map(syllabusRepository.findById(1L), SyllabusListDto.class);

    syllabusTypeMap.addMappings(mapping -> {
      mapping.map(src -> src.getSessions().get(0).getName(), SyllabusListDto::setName);
    });

    // Converter<Syllabus, SyllabusListDto> syllabusConverter = new Converter<Syllabus, SyllabusListDto>() {
    //   @Override
    //   public SyllabusListDto convert(MappingContext<Syllabus, SyllabusListDto> context) {
    //     Syllabus src = context.getSource();
    //     SyllabusListDto dest = new SyllabusListDto();

    //     List<OutputStandard> outputStandards = new ArrayList<>();
    //     List<Session> sessions = src.getSessions();
    //     if (sessions != null) {
    //       for (Session session : sessions) {
    //         List<Unit> units = session.getUnits();
    //         if (units != null) {
    //           for (Unit unit : units) {
    //             List<Lesson> lessons = unit.getLessons();
    //             if (lessons != null) {
    //               for (Lesson lesson : lessons) {
    //                 OutputStandard outputStandard = lesson.getOutputStandard();
    //                 if (outputStandard != null) {
    //                   outputStandards.add(outputStandard);
    //                 }
    //               }
    //             }
    //           }
    //         }
    //       }
    //     } else {
    //       System.out.println("sessions: null");
    //     }
    //     dest.setOutputStandard(outputStandards);
    //     dest.setCreatedBy(src.getCreatedBy().getFullName());

    //     return dest;
    //   }
    // };

    // TypeMap<Syllabus, SyllabusListDto> typeMap = modelMapper.getTypeMap(Syllabus.class, SyllabusListDto.class);
    // modelMapper.addConverter(syllabusConverter);

    // if (typeMap == null) {
    //   typeMap =
    //     modelMapper
    //       .createTypeMap(Syllabus.class, SyllabusListDto.class)
    //       .addMappings(mapper -> {
    //         mapper.map(
    //           src -> {
    //             List<OutputStandard> outputStandards = new ArrayList<>();
    //             List<Session> sessions = src.getSessions();
    //             if (sessions != null) {
    //               for (Session session : sessions) {
    //                 List<Unit> units = session.getUnits();
    //                 if (units != null) {
    //                   for (Unit unit : units) {
    //                     List<Lesson> lessons = unit.getLessons();
    //                     if (lessons != null) {
    //                       for (Lesson lesson : lessons) {
    //                         OutputStandard outputStandard = lesson.getOutputStandard();
    //                         if (outputStandard != null) {
    //                           outputStandards.add(outputStandard);
    //                         }
    //                       }
    //                     }
    //                   }
    //                 }
    //               }
    //             } else {
    //               System.out.println("sessions: null");
    //             }
    //             return outputStandards;
    //           },
    //           SyllabusListDto::setOutputStandard
    //         );
    //         mapper.map(
    //           src -> {
    //             return src.getCreatedBy().getFullName();
    //           },
    //           SyllabusListDto::setCreatedBy
    //         );
    //       });
    // }
    List<Syllabus> sa = syllabusRepository.findAll();
    return syllabusRepository.findAll(pageable).map(s -> modelMapper1.map(s, SyllabusListDto.class));
  }
  // public SyllabusListDto findAll() {
  //   SyllabusListDto s = syllabusRepository.findById(1L).map(sy -> modelMapper.map(sy, SyllabusListDto.class)).get();

  //   List<OutputStandard> outputStandards = new ArrayList<>();
  //   List<Session> sessions = syllabusRepository.findById(1L).get().getSessions();
  //   if (sessions != null) {
  //     for (Session session : sessions) {
  //       List<Unit> units = session.getUnits();
  //       if (units != null) {
  //         for (Unit unit : units) {
  //           List<Lesson> lessons = unit.getLessons();
  //           if (lessons != null) {
  //             for (Lesson lesson : lessons) {
  //               OutputStandard outputStandard = lesson.getOutputStandard();
  //               if (outputStandard != null) {
  //                 outputStandards.add(outputStandard);
  //               }
  //             }
  //           }
  //         }
  //       }
  //     }
  //   }
  //   s.setOutputStandard(null);
  //   return s;
  // }
}

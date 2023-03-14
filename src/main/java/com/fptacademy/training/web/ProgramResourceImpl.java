package com.fptacademy.training.web;

import com.fptacademy.training.service.ProgramService;
import com.fptacademy.training.service.SyllabusService;
import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.service.dto.SyllabusDto;
import com.fptacademy.training.service.mapper.ProgramMapper;
import com.fptacademy.training.web.api.ProgramResource;
import com.fptacademy.training.web.vm.ProgramVM;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProgramResourceImpl implements ProgramResource {
    private final ProgramService programService;
    private final SyllabusService syllabusService;
    private final ResourceLoader resourceLoader;

    private final ProgramMapper programMapper;
    @Override
    public ResponseEntity<ProgramDto> createProgram(ProgramVM programVM) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(programService.createProgram(programVM));
    }

    //Activate the Program by id
    @Override
    public ResponseEntity<ProgramDto> activateProgram(Long id) {
        ProgramDto programDto=programService.activateProgram(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programDto);
    }

    @Override
    public ResponseEntity<List<ProgramDto>> getPrograms(List<String> keywords, String sort, int page, int size) {
        List<ProgramDto> programDtos = programService.getPrograms(keywords, sort);
        // Apply pagination
        int start = page * size;
        int end = Math.min(start + size, programDtos.size());
        Page<ProgramDto> pageResult = new PageImpl<>(
                programDtos.subList(start, end),
                PageRequest.of(page, size),
                programDtos.size());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResult.getContent());
    }
    @Override
    public ResponseEntity<List<SyllabusDto.SyllabusListDto>>getSyllabusesByName(String name) {
        List<SyllabusDto.SyllabusListDto> syllabusDtos = syllabusService.findSyllabusesByName(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(syllabusDtos);
    }

    @Override
    public ResponseEntity<List<SyllabusDto.SyllabusListDto>> getSyllabusesByProgramId(Long id) {
        List<SyllabusDto.SyllabusListDto> syllabusDtos = programService.findSyllabusesByProgramId(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(syllabusDtos);
    }

    @Override
    public ResponseEntity<ProgramDto> getProgramById(Long id) {
        ProgramDto programDto = programService.findProgramByProgramId(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programDto);
    }


    @Override
    public ResponseEntity<Resource> downloadExcelTemplate() {
        Resource resource = resourceLoader.getResource("classpath:templates/Program-Template.xlsx");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Program-Template.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Override
    public ResponseEntity<List<ProgramDto>> importProgramsFromExcel(MultipartFile file, String[] properties, String handler) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(programService.importProgramFromExcel(file, properties, handler));
    }

    @Override
    public ResponseEntity<ProgramDto> deactivateProgram(Long id) {
        ProgramDto programDto = programMapper.toDto(programService.deactivateProgram(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(programDto);
    }

    @Override
    public ResponseEntity<ProgramDto> updateProgram(ProgramVM programVM, Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(programService.updateProgram(programVM, id));
    }


    @Override
    public void deleteProgram(Long id) {
        programService.deleteProgram(id);
    }
}

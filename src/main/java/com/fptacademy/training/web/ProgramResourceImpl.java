package com.fptacademy.training.web;

import com.fptacademy.training.service.ProgramService;
import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.web.api.ProgramResource;
import com.fptacademy.training.web.vm.ProgramVM;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProgramResourceImpl implements ProgramResource {
    private final ProgramService programService;
    @Override
    public ResponseEntity<ProgramDto> createProgram(ProgramVM programVM) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(programService.createProgram(programVM));
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
    public ResponseEntity<Resource> downloadExcelTemplate() {
        return null;
    }

    @Override
    public ResponseEntity<List<ProgramDto>> importProgramsFromExcel(MultipartFile file, String[] properties, String handler) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(programService.importProgramFromExcel(file, properties, handler));
    }
}

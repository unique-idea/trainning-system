package com.fptacademy.training.web;

import com.fptacademy.training.service.ProgramService;
import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.web.api.ProgramResource;
import com.fptacademy.training.web.vm.ProgramVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
}

package com.fptacademy.training.service;

import com.fptacademy.training.domain.Program;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.exception.ResourceAlreadyExistsException;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.ProgramRepository;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.service.mapper.ProgramMapper;
import com.fptacademy.training.web.vm.ProgramVM;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
public class ProgramService {
    private final ProgramRepository programRepository;
    private final SyllabusRepository syllabusRepository;
    private final ProgramMapper programMapper;

    public ProgramDto createProgram(ProgramVM programVM) {
        // Check if program name already existed or not
        if (programRepository.existsByName(programVM.name())) {
            throw new ResourceAlreadyExistsException("Training program with name '" + programVM.name() + "' already existed");
        }
        // Create new program
        Program program = new Program();
        program.setName(programVM.name());
        // Add list of syllabuses to program
        List<Syllabus> syllabuses = programVM.syllabusIds()
                .stream()
                .map(id -> syllabusRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Syllabus with ID " + id + " not found")))
                .toList();
        program.setSyllabuses(syllabuses);
        // Save program
        programRepository.save(program);

        return programMapper.toDto(program);
    }

    //@PostFilter("hasAuthority(\"" + Permissions.PROGRAM_VIEW + "\") and filterObject.activated == true")
    public List<ProgramDto> getPrograms(List<String> keywords, String sort, int page, int size) {
        // Get training programs based on keyword or get all if there's no keyword
        List<Program> programs;
        if (keywords != null) {
            List<Program> firstFilteredPrograms = programRepository.findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCase(keywords.get(0), keywords.get(0));
//            programs = new ArrayList<>(programRepository.findAll());
            if (keywords.size() > 1) {
                keywords.remove(0);
//                for (int i = 1; i < keywords.size(); ++i) {
//                    int finalI = i;
                    programs = firstFilteredPrograms
                            .stream()
                            .filter(p -> keywords
                                    .stream()
                                    .allMatch(e -> p.getName().toLowerCase().contains(e.toLowerCase()) || 
                                            p.getCreatedBy().getFullName().toLowerCase().contains(e.toLowerCase()))).toList();
//                }
            } else {
                programs = firstFilteredPrograms;
            }
        } else {
            programs = programRepository.findAll();
        }
        // Convert list of program entities to list of program DTOs
        List<ProgramDto> programDtos = new ArrayList<>(programMapper.toDtos(programs));
        // Sort the list
        String[] a = sort.split(",");
        if (a.length != 2) {
            throw new ResourceBadRequestException("Invalid parameters");
        }
        String property = a[0];
        String direction = a[1];
        Comparator<ProgramDto> idComparator = Comparator.comparing(ProgramDto::getId);
        Comparator<ProgramDto> nameComparator = Comparator.comparing(ProgramDto::getName);
        Comparator<ProgramDto> createdByComparator = Comparator.comparing(e -> e.getCreatedBy().getName());
        Comparator<ProgramDto> createdAtComparator = Comparator.comparing(ProgramDto::getCreatedAt);
        Comparator<ProgramDto> durationComparator = Comparator.comparing(ProgramDto::getDurationInDays);
        if (direction.equals("desc")) {
            idComparator = idComparator.reversed();
            nameComparator = nameComparator.reversed();
            createdByComparator = createdByComparator.reversed();
            createdAtComparator = createdAtComparator.reversed();
            durationComparator = durationComparator.reversed();
        }
        switch (property) {
            case "id" -> programDtos.sort(idComparator);
            case "name" -> programDtos.sort(nameComparator);
            case "createdAt" -> programDtos.sort(createdAtComparator);
            case "createdBy" -> programDtos.sort(createdByComparator);
            case "duration" -> programDtos.sort(durationComparator);
        }
        // Apply pagination
        int start = page * size;
        int end = Math.min(start + size, programDtos.size());
        Page<ProgramDto> pageResult = new PageImpl<>(
                programDtos.subList(start, end),
                PageRequest.of(page, size),
                programDtos.size());

        return pageResult.getContent();
    }
}

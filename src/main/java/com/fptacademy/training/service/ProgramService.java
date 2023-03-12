package com.fptacademy.training.service;

import com.fptacademy.training.domain.Program;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.exception.ResourceAlreadyExistsException;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.ProgramRepository;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.security.Permissions;
import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.service.mapper.ProgramMapper;
import com.fptacademy.training.web.vm.ProgramVM;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
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
    /*
    Return list of program DTOs, only return ones that are activated if logged-in user has View permission,
    otherwise return all
     */
    @PostFilter("(hasAnyAuthority('" + Permissions.PROGRAM_VIEW + "') and filterObject.activated == true) or " +
            "(hasAnyAuthority('" +
            Permissions.PROGRAM_CREATE + "', '" +
            Permissions.PROGRAM_MODIFY + "', '" +
            Permissions.PROGRAM_FULL_ACCESS + "'))")
    public List<ProgramDto> getPrograms(List<String> keywords, String sort) {
        // Get training programs based on keywords or get all if there's no keyword
        List<Program> programs;
        if (keywords != null) {
            List<Program> firstFilteredPrograms = programRepository
                    .findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCase(keywords.get(0), keywords.get(0));
            if (keywords.size() > 1) {
                programs = firstFilteredPrograms
                        .stream()
                        .skip(1)
                        .filter(p -> keywords
                                .stream()
                                .allMatch(e -> p.getName().toLowerCase().contains(e.toLowerCase()) ||
                                        p.getCreatedBy().getFullName().toLowerCase().contains(e.toLowerCase())))
                        .toList();
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
            throw new ResourceBadRequestException("Invalid parameter for sort");
        }
        String property = a[0];
        String direction = a[1];
        if (!direction.equals("asc") && !direction.equals("desc")) {
            throw new ResourceBadRequestException("Invalid parameter for sort, cannot find sort direction (asc or desc)");
        }
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
            default -> throw new ResourceBadRequestException("Invalid parameter for sort, there's no such property");
        }

        return programDtos;
    }
}

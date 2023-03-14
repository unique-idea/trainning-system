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
import com.fptacademy.training.service.dto.SyllabusDto;
import com.fptacademy.training.service.mapper.ProgramMapper;
import com.fptacademy.training.service.mapper.SyllabusMapper;
import com.fptacademy.training.web.vm.ProgramVM;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
public class ProgramService {
    private final ProgramRepository programRepository;
    private final SyllabusRepository syllabusRepository;
    private final ProgramMapper programMapper;

    private final SyllabusMapper syllabusMapper;

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

    public List<SyllabusDto.SyllabusListDto> findSyllabusesByProgramId(Long id){
        // Check if program id already existed or not
        if (!programRepository.existsById(id)) {
            throw new ResourceNotFoundException("Training program with id '" + id + "' not existed");
        }
        // Get program by id
        Optional<Program> program = programRepository.findById(id);

        // Get list syllabus of program
        List<SyllabusDto.SyllabusListDto> syllabusDtos = syllabusMapper.toDtos(program.get().getSyllabuses());
        return syllabusDtos;
    }

    //tai nguyen
    public ProgramDto findProgramByProgramId(Long id){
        // Check if program id already existed or not
        if (!programRepository.existsById(id)) {
            throw new ResourceNotFoundException("Training program with id '" + id + "' not existed");
        }
        // Get program by id
        Optional<Program> program = programRepository.findById(id);
        ProgramDto programDto = programMapper.toDto(program.get());
        // Get list sylla    List<SyllabusDto.SyllabusListDto> syllabusDtos = syllabusMapper.toDtos(program.get().getSyllabuses());bus of program
//
        return programDto;
    }

    //tai nguyen

    private Program replaceProgram(Program from, Program to) {
        to.setName(from.getName());
        to.setSyllabuses(from.getSyllabuses());
        to.setActivated(from.getActivated());
        return to;
    }

    private List<Program> replaceAllProgramsHaveName(String name, Program from) {
        List<Program> programs = programRepository.findByName(name);
        programs.forEach(p -> replaceProgram(from, p));
        return programs;
    }

    public List<ProgramDto> importProgramFromExcel(MultipartFile file, String[] properties, String handler) {
        List<Program> newPrograms = new ArrayList<>();
        List<Program> updatePrograms = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            row_loop:
            for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
                Row row = sheet.getRow(i);
                // Create a program object with the information from Excel file
                Program program = new Program();
                if (row.getCell(0).getCellType() != CellType.BLANK) {
                    Long id = (long)row.getCell(0).getNumericCellValue();
                    program.setId(id);
                }
                program.setName(row.getCell(1).getStringCellValue().trim());
                List<Syllabus> syllabuses = Arrays.stream(row.getCell(2).getStringCellValue().trim().split(","))
                        .map(code -> syllabusRepository
                                .findByCode(code)
                                .orElseThrow(() -> new ResourceNotFoundException("Syllabus with code '" + code + "' not found")))
                        .toList();
                program.setSyllabuses(syllabuses);
                program.setActivated(row.getCell(3).getBooleanCellValue());

                for (String p : properties) {
                    if (p.equals("id") && program.getId() != null) {
                        Program existingProgram = programRepository
                                .findById(program.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Program with ID '" + program.getId() + "' not found"));
                        switch (handler) {
                            case "replace" -> {
                                updatePrograms.add(replaceProgram(program, existingProgram));
                                continue row_loop;
                            }
                            case "skip", "allow" -> {
                                continue row_loop;
                            }
                        }
                    } else if (p.equals("name") && programRepository.existsByName(program.getName())) {
                        switch (handler) {
                            case "replace" -> {
                                updatePrograms.addAll(replaceAllProgramsHaveName(program.getName(), program));
                                continue row_loop;
                            }
                            case "skip" -> {
                                continue row_loop;
                            }
                            case "allow" -> {
                                newPrograms.add(program);
                                continue row_loop;
                            }
                        }
                    }
                }
                if (programRepository.existsByName(program.getName())) {
                    throw new ResourceAlreadyExistsException("Program with name '" + program.getName() + "' already existed");
                }
                newPrograms.add(program);
            }
        } catch (NumberFormatException e) {
            throw new ResourceBadRequestException("Invalid ID format", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read excel file", e);
        }

        List<Program> programs = new ArrayList<>(programRepository.saveAll(newPrograms));
        programs.addAll(updatePrograms);
        return programMapper.toDtos(programs);
    }
}

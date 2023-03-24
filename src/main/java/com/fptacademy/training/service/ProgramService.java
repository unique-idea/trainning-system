package com.fptacademy.training.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.fptacademy.training.repository.ClassRepository;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fptacademy.training.domain.Class;
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

@RequiredArgsConstructor
@Service
@Transactional
public class ProgramService {
    private final ProgramRepository programRepository;
    private final SyllabusRepository syllabusRepository;
    private final ProgramMapper programMapper;
    private final ClassRepository classRepository;
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
    public List<ProgramDto> getPrograms(List<String> keywords, Boolean activated, String sort) {
        // Get training programs based on keywords or get all if there's no keyword
        List<Program> programs;
        if (keywords != null) {
            List<Program> firstFilteredPrograms =
                    activated != null ?
                            programRepository
                                    .findByNameContainsIgnoreCaseOrCreatedBy_FullNameContainsIgnoreCaseAndActivated(keywords.get(0), keywords.get(0), activated) :
                            programRepository
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
            programs = activated != null ?
                    programRepository.findByActivated(activated) :
                    programRepository.findAll();
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
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training program with id '" + id + "' not existed"));
        return syllabusMapper.toDtos(program.getSyllabuses());
    }

    //tai nguyen
    public ProgramDto findProgramByProgramId(Long id){
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training program with id '" + id + "' not existed"));
        return programMapper.toDto(program);
    }

    //tai nguyen

    private Program replaceProgram(Program from, Program to) {
        to.setName(from.getName());
        to.setSyllabuses(from.getSyllabuses());
        if (!isDeactivatable(to.getId())) {
            throw new ResourceBadRequestException("Cannot deactivate training program with ID '" + to.getId() +
                    "' because there are on-going classes depend on this program");
        }
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
                if (row.getCell(0) != null && row.getCell(0).getCellType() == CellType.NUMERIC) {
                    Long id = (long)row.getCell(0).getNumericCellValue();
                    program.setId(id);
                } else if (row.getCell(0) != null &&
                        row.getCell(0).getCellType() == CellType.STRING &&
                        StringUtils.hasText(row.getCell(0).getStringCellValue())) {
                    throw new ResourceBadRequestException("Excel file wrong format at Program ID column, make sure to specify right ID format. " +
                            "If not specify ID, please make sure ID cell is empty");
                }
                program.setName(row.getCell(1).getStringCellValue().trim());
                String syllabusCodes = row.getCell(2).getStringCellValue().trim();
                if (!StringUtils.hasText(program.getName()) || !StringUtils.hasText(syllabusCodes)) {
                    continue;
                }
                List<Syllabus> syllabuses = Arrays.stream(syllabusCodes.split(","))
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

    public ProgramDto activateProgram(Long id) {
        Program program = programRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program with ID '" + id + "' not found"));
        program.setActivated(true);
        programRepository.saveAndFlush(program);
        return programMapper.toDto(program);
    }

    public boolean isDeactivatable(Long id) {
        List<Class> classes = classRepository.findByProgram_Id(id);
        if (classes.size() == 0) {
            return true;
        }
        for (Class c : classes) {
            if (!c.getClassDetail().getStatus().equals("CLOSED")) {
                return false;
            }
        }
        return true;
    }

    public Program deactivateProgram(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program with ID '" + id + "' not found"));
        if(!isDeactivatable(id))
            throw new ResourceBadRequestException("Cannot deactivate this training program because there are on-going classes depend on this program");
        program.setActivated(false);
        return program;
    }

    public ProgramDto updateProgram(ProgramVM programVM, Long id) {
        Program p = programRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Program with ID '" + id + "' not found"));
        p.setName(programVM.name());
        List<Syllabus> syllabuses = new ArrayList<>(programVM.syllabusIds()
                .stream()
                .map(syllabusId -> syllabusRepository
                        .findById(syllabusId)
                        .orElseThrow(() -> new ResourceNotFoundException("Syllabus with ID " + syllabusId + " not found")))
                .toList());
        p.setSyllabuses(syllabuses);
        programRepository.saveAndFlush(p);
        return programMapper.toDto(p);
    }

    public void deleteProgram(Long id) {
        if (classRepository.findByProgram_Id(id).size() != 0) {
            throw new ResourceBadRequestException("Can not delete program!");
        }
        var program = programRepository.findById(id);
        if (program.isEmpty()) {
            throw new ResourceNotFoundException("Can not find program");
        }
        programRepository.deleteById(id);
    }
}

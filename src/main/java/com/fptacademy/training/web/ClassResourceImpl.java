package com.fptacademy.training.web;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.domain.ClassDetail;
import com.fptacademy.training.repository.ClassDetailRepository;
import com.fptacademy.training.repository.ClassRepository;
import com.fptacademy.training.service.ClassService;
import com.fptacademy.training.service.dto.*;
import com.fptacademy.training.service.mapper.ClassDetailMapper;
import com.fptacademy.training.service.mapper.ClassMapper;
import com.fptacademy.training.web.api.ClassResource;
import com.fptacademy.training.web.vm.ClassListResponseVM;
import com.fptacademy.training.web.vm.ClassVM;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ClassResourceImpl implements ClassResource {
    private final ClassService classService;



    @Override
    public ResponseEntity<ClassDto> getClassById(Long class_id){
        ClassDto classes = classService.getById(class_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classes);
    }

    @Override
    public ResponseEntity<ClassDetailDto> getDetailsByClassId(Long class_id){
        ClassDetailDto details = classService.getDetailsByClass_Id(class_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(details);
    }

    @Override
    public ResponseEntity<ClassListResponseVM> filterClass(List<String> keywords,
                                                      LocalDate from,
                                                      LocalDate to,
                                                      List<String> cities,
                                                      List<String> classTimes,
                                                      List<String> statuses,
                                                      List<String> attendeeTypes,
                                                      String fsu,
                                                      String trainerCode,
                                                      String sort,
                                                      int page, int size) {
        List<ClassDto> classDtos = classService.filterClass(
                keywords, from, to,
                cities,
                classTimes,
                statuses,
                attendeeTypes,
                fsu,
                trainerCode,
                sort);
        List<ClassDto> result;
        int totalElements = classDtos.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        // Apply pagination
        int start = (page - 1) * size;
        int end = Math.min(start + size, classDtos.size());
        if (start > end) result = new ArrayList<>();
        else {
            Page<ClassDto> pageResult = new PageImpl<>(
                    classDtos.subList(start, end),
                    PageRequest.of(page, size),
                    classDtos.size());
            result = pageResult.getContent();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ClassListResponseVM(totalPages, totalElements, size, page, result));
    }


    @Override
    public ResponseEntity<String> delClass(Long id) {
        classService.deleteClass(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Delete class successfully");
    }

    @Override
    public ResponseEntity<String> deactivateClass(Long id) {
        classService.deactivateClass(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Deactivate class successfully");
    }

    @Override
    public ResponseEntity<String> activateClass(Long id) {
        classService.activateClass(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Activate class successfully");
    }

    @Override
    public ResponseEntity<ClassDetailDto> createClass(ClassVM classVM) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classService.createClass(classVM));
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllTrainer() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classService.getAllTrainer());
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllClassAdmin() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classService.getAllClassAdmin());
    }

    @Override
    public ResponseEntity<List<AttendeeDto>> getAllAttendees() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classService.getAllAttendees());
    }

    @Override
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classService.getAllLocations());
    }

    @Override
    public ResponseEntity<ClassDetailDto> updateClass(Long class_id, ClassVM classVM) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(classService.updateClass(class_id, classVM));
    }


}

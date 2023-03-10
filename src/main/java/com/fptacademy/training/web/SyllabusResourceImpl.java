package com.fptacademy.training.web;

import com.fptacademy.training.domain.*;
import com.fptacademy.training.service.*;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.repository.LessonRepository;
import com.fptacademy.training.repository.LevelRepository;
import com.fptacademy.training.repository.OutputStandardRepository;

import java.util.ArrayList;

import com.fptacademy.training.repository.SyllabusRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fptacademy.training.service.dto.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SyllabusResourceImpl {

    private final ModelMapper modelMapper;
    private final OutputStandardService outputStandardService;
    private final OutputStandardRepository outputStandardRepository;
    private final LevelService levelService;
    private final LessonService lessonService;
    private final UnitService unitService;
    private final MaterialService materialService;
    private final AssessmentService assessmentService;
    private final FormatTypeService formatTypeService;
    private final DeliveryService deliveryService;
    private final SyllabusService syllabusService;

    /**
     * Output Standards
     */
    //region OutputStandard
    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @PostMapping("/outputStandards")
    public ResponseEntity<OutputStandard> createOutputStandard(@RequestParam(required = false) String name) {
//        if(outputStandard.getId() != null) {
//            throw new ResourceBadRequestException("A new OutputStandard cannot already have an ID");
//        }
        OutputStandard result = new OutputStandard();
        result.setName(name);
        outputStandardService.save(result);
        return ResponseEntity.ok(result);
    }


    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @PutMapping(value = "/outputStandards/{id}")
    public ResponseEntity<OutputStandard> updateOutputStandard(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OutputStandard outputStandard
    ) {
        Optional<OutputStandard> result = outputStandardService.update(id, outputStandard);
        return result.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @GetMapping("/outputStandards")
    public ResponseEntity<List<OutputStandard>> getAllOutputStandards() {
        List<OutputStandard> list = outputStandardService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @GetMapping("/OutputStandards/{id}")
    public ResponseEntity<OutputStandard> getOutputStandard(@PathVariable Long id) {
        Optional<OutputStandard> outputStandard = outputStandardService.findOne(id);
        return outputStandard.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/outputStandards/findName")
    public ResponseEntity<List<OutputStandard>> getOutputStandardByName(@RequestParam(value = "name", required = true) String name) {
        List<OutputStandard> outputStandards = outputStandardService.getOutputStandardByName(name);
        return ResponseEntity.ok().body(outputStandards);
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @DeleteMapping("/outputStandards/{id}")
    public ResponseEntity<?> deleteOutputStandard(@PathVariable Long id) {
        outputStandardService.delete(id);
        return ResponseEntity.ok("OK");
    }
    //endregion

    /**
     * Level
     */
    //region Level
    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @PostMapping(path = "/level")
    public ResponseEntity<Level> createLevel(@RequestParam(required = false) String name) {
//        if(level.getId() != null) {
//            throw new ResourceBadRequestException("A new Level cannot already have an ID");
//        }
        Level result = new Level();
        result.setName(name);
        levelService.save(result);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @PutMapping(path = "/level/{id}")
    public ResponseEntity<Level> updateLevel(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestParam(required = false) String name
    ) {
        Level level = new Level();
        level.setId(id);
        level.setName(name);
        Optional<Level> result = levelService.update(level);
        return result.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @GetMapping("/level")
    public ResponseEntity<List<Level>> getAllLevel() {
        List<Level> levels = levelService.getAllLevel();
        return ResponseEntity.ok().body(levels);
    }


    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @GetMapping("/level/{id}")
    public ResponseEntity<Level> getLevel(@PathVariable Long id) {
        Optional<Level> level = levelService.findOne(id);
        return level.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/level/findByName")
    public ResponseEntity<List<Level>> getLevelByName(@RequestParam(value = "name", required = true) String name) {
        List<Level> levels = levelService.getLevelByName(name);
        return ResponseEntity.ok().body(levels);
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @DeleteMapping("/levels/{id}")
    public ResponseEntity<?> deleteLevel(@PathVariable Long id) {
        levelService.delete(id);
        return ResponseEntity.ok("OK");
    }
    //endregion

    /**
     * Lesson
     */
    //region Lesson
//    @PostMapping(path = "/lesson")
//    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
//        if(lesson.getId() != null) {
//            throw new ResourceBadRequestException("A new Lesson cannot already have an ID");
//        }
//        Lesson result = lessonService.save(lesson);
//        return ResponseEntity.ok(result);
//    }
//
//    @PutMapping(path = "/lesson/{id}")
//    public ResponseEntity<Lesson> updateLesson(
//            @PathVariable(value = "id", required = false) final Long id,
//            @RequestBody Lesson lesson
//    ) {
//        return null;
//    }
    @GetMapping("/lesson")
    public ResponseEntity<List<LessonDTO>> getAllLesson() {
        return ResponseEntity.ok().body(lessonService.getAll());
    }

    @GetMapping("/lesson/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        System.out.println("HELLO " + id);
        Optional<LessonDTO> lesson = lessonService.getLessonByID(id);
        return lesson.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/lesson/findByName/{name}")
    public ResponseEntity<List<LessonDTO>> getLessonByName(@PathVariable String name) {
        List<LessonDTO> lessons = lessonService.getLessonByName(name);
        return ResponseEntity.ok().body(lessons);
    }

//    @DeleteMapping("/lesson/{id}")
//    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
//        levelService.delete(id);
//        return ResponseEntity.ok("OK");
//    }
    //endregion

    /**
     * Unit
     */
    //region Unit
//    @PostMapping(path = "/unit")
//    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
//        if(unit.getId() != null) {
//            throw new ResourceBadRequestException("A new Level cannot already have an ID");
//        }
//        Unit result = unitService.save(unit);
//        return ResponseEntity.ok(result);
//    }
//
//    @PutMapping(path = "/unit/{id}")
//    public ResponseEntity<Unit> updateUnit(
//            @PathVariable(value = "id", required = false) final Long id,
//            @RequestBody Unit unit
//    ) {
//        return null;
//    }
    @GetMapping("/unit")
    public ResponseEntity<List<UnitDTO>> getAllUnit() {
        return ResponseEntity.ok().body(unitService.getAll());
    }

    @GetMapping("/unit/{id}")
    public ResponseEntity<UnitDTO> getUnit(@PathVariable Long id) {
        Optional<UnitDTO> unit = unitService.getUnitByID(id);
        return unit.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/unit/findByName/{name}")
    public ResponseEntity<List<UnitDTO>> getUnitByName(@PathVariable String name) {
        List<UnitDTO> units = unitService.getUnitByName(name);
        return ResponseEntity.ok().body(units);
    }

//    @DeleteMapping("/unit/{id}")
//    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {
//        unitService.delete(id);
//        return ResponseEntity.ok("OK");
//    }
    //endregion

    /**
     * Material
     */
    //region Material
//    @PostMapping(path = "/material")
//    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
//        if(material.getId() != null) {
//            throw new ResourceBadRequestException("A new Level cannot already have an ID");
//        }
//        return null;
//    }
//
//    @PutMapping(path = "/material/{id}")
//    public ResponseEntity<Material> updateLevel(
//            @PathVariable(value = "id", required = false) final Long id,
//            @RequestBody Material material
//    ) {
//        return null;
//    }
    @GetMapping("/material")
    public ResponseEntity<List<MaterialDTO>> getAllMaterial() {
        return ResponseEntity.ok().body(materialService.getAll());
    }

    @GetMapping("/material/{id}")
    public ResponseEntity<MaterialDTO> getMaterial(@PathVariable Long id) {
        Optional<MaterialDTO> material = materialService.getMaterialByID(id);
        return material.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/material/findByName/{name}")
    public ResponseEntity<List<MaterialDTO>> getMaterialByName(@PathVariable String name) {
        List<MaterialDTO> material = materialService.getMaterialByName(name);
        return ResponseEntity.ok().body(material);
    }

//    @DeleteMapping("/material/{id}")
//    public ResponseEntity<?> deleteMaterial(@PathVariable Long id) {
//        return null;
//    }
    //endregion

    /**
     * Assessment
     */
    //region Assessment
//    @PostMapping(path = "/assessment")
//    public ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment) {
//        if(assessment.getId() != null) {
//            throw new ResourceBadRequestException("A new Level cannot already have an ID");
//        }
//        return null;
//    }
//
//    @PutMapping(path = "/assessment/{id}")
//    public ResponseEntity<Assessment> updateAssessment(
//            @PathVariable(value = "id", required = false) final Long id,
//            @RequestBody Assessment assessment
//    ) {
//        return null;
//    }
    @GetMapping("/assessment")
    public ResponseEntity<List<Assessment>> getAssessment() {
        return ResponseEntity.ok().body(assessmentService.getAll());
    }

    @GetMapping("/assessment/{id}")
    public ResponseEntity<Assessment> getAssessment(@PathVariable Long id) {
        Optional<Assessment> assessment = assessmentService.getAssessmentByID(id);
        return assessment.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

//    @DeleteMapping("/assessment/{id}")
//    public ResponseEntity<?> deleteAssessment(@PathVariable Long id) {
//        return null;
//    }
    //endregion

    /**
     * Format type
     */
    //region Format type
//    @PostMapping(path = "/formattype")
//    public ResponseEntity<FormatType> createFormatType(@RequestBody FormatType formatType) {
//        if(formatType.getId() != null) {
//            throw new ResourceBadRequestException("A new Level cannot already have an ID");
//        }
//        return null;
//    }
//
//    @PutMapping(path = "/formattype/{id}")
//    public ResponseEntity<FormatType> updateFormatType(
//            @PathVariable(value = "id", required = false) final Long id,
//            @RequestBody FormatType formatType
//    ) {
//        return null;
//    }
    @GetMapping("/formattype")
    public ResponseEntity<List<FormatType>> getFormatType() {
        return ResponseEntity.ok().body(formatTypeService.getAll());
    }

    @GetMapping("/formattype/{id}")
    public ResponseEntity<FormatType> getFormatType(@PathVariable Long id) {
        Optional<FormatType> formatType = formatTypeService.getFormatTypeByID(id);
        return formatType.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }


    @GetMapping("/formattype/findByName/{name}")
    public ResponseEntity<List<FormatType>> getFormatTypeByName(@PathVariable String name) {
        List<FormatType> formatTypes = formatTypeService.getFormatTypeByName(name);
        return ResponseEntity.ok().body(formatTypes);
    }

//    @DeleteMapping("/formattype/{id}")
//    public ResponseEntity<?> deleteFormatType(@PathVariable Long id) {
//        return null;
//    }
    //endregion

    /**
     * Delivery
     */
    //region Delivery
    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @PostMapping("/delivery")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        if (delivery.getId() != null) {
            throw new ResourceBadRequestException("A new Delivery cannot already have an ID");
        }
        Delivery result = deliveryService.save(delivery);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @PutMapping(value = "/delivery/{id}")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable(value = "id", required = false) final Long id, @RequestBody Delivery delivery) {
        if (delivery.getId() == null) {
            throw new ResourceBadRequestException("id null");
        }
        if (!Objects.equals(id, delivery.getId())) {
            throw new ResourceBadRequestException("id invalid");
        }

//        if (!deliveryRepository.existsById(id)) {
//            throw new ResourceBadRequestException("Entity not found id ");
//        }

        Optional<Delivery> result = deliveryService.update(delivery);

        return result.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @GetMapping("/delivery")
    public ResponseEntity<List<Delivery>> getAllDeliverys() {
        List<Delivery> list = deliveryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @GetMapping("/delivery/{id}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
        Optional<Delivery> delivery = deliveryService.findOne(id);
        return delivery.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @DeleteMapping("/delivery/{id}")
    public ResponseEntity<?> deleteDelivery(@PathVariable Long id) {
        deliveryService.delete(id);
        return ResponseEntity.ok("OK");
    }
    //endregion

    /**
     * Syllabus
     */
    //region Syllabus
    @Operation(
            summary = "createdBy.code,asc",
            description = "createdBy.code,asc",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @GetMapping(value = "/syllabuses")
    @PreAuthorize("!hasAuthority('Syllabus_AccessDenied')")
    public ResponseEntity<List<SyllabusDto.SyllabusListDto>> getAllSyllabuses(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable,
            @RequestParam(required = false) String[] keywords,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant[] createDate,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                syllabusService.findAll(SyllabusRepository.searchByKeywordsOrBycreateDates(keywords, createDate, authentication), pageable).getContent()
        );
    }
    //endregion
}

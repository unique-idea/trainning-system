package com.fptacademy.training.web;

import com.fptacademy.training.domain.Delivery;
import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.repository.DeliveryRepository;
import com.fptacademy.training.repository.LevelRepository;
import com.fptacademy.training.repository.OutputStandardRepository;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.DeliveryService;
import com.fptacademy.training.service.LevelService;
import com.fptacademy.training.service.OutputStandardService;
import com.fptacademy.training.service.SyllabusService;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
  private final LevelRepository levelRepository;
  private final DeliveryService deliveryService;
  private final DeliveryRepository deliveryRepository;

  private final SyllabusRepository syllabusRepository;
  private final SyllabusService syllabusService;

  @Operation(summary = "", description = "", tags = "outputStandards", security = @SecurityRequirement(name = "token_auth"))
  // OutputStandards
  @PostMapping("/outputStandards")
  public ResponseEntity<OutputStandard> createOutputStandard(@RequestBody OutputStandard OutputStandardDTO) {
    if (OutputStandardDTO.getId() != null) {
      throw new ResourceBadRequestException("A new OutputStandard cannot already have an ID");
    }
    OutputStandard result = outputStandardService.save(OutputStandardDTO);
    return ResponseEntity.ok(result);
  }

  @Operation(summary = "", description = "", tags = "outputStandards", security = @SecurityRequirement(name = "token_auth"))
  @PutMapping("/outputStandards/{id}")
  public ResponseEntity<OutputStandard> updateOutputStandard(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody OutputStandard OutputStandardDTO
  ) {
    if (OutputStandardDTO.getId() == null) {
      throw new ResourceBadRequestException("id null");
    }
    if (!Objects.equals(id, OutputStandardDTO.getId())) {
      throw new ResourceBadRequestException("id invalid");
    }

    if (!outputStandardRepository.existsById(id)) {
      throw new ResourceBadRequestException("Entity not found id ");
    }

    Optional<OutputStandard> result = outputStandardService.update(OutputStandardDTO);

    return result.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "", description = "", tags = "outputStandards", security = @SecurityRequirement(name = "token_auth"))
  @GetMapping("/outputStandards")
  public ResponseEntity<List<OutputStandard>> getAllOutputStandards() {
    List<OutputStandard> list = outputStandardService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @Operation(summary = "", description = "", tags = "outputStandards", security = @SecurityRequirement(name = "token_auth"))
  @GetMapping("/outputStandards/{id}")
  public ResponseEntity<OutputStandard> getOutputStandard(@PathVariable Long id) {
    Optional<OutputStandard> outputStandard = outputStandardService.findOne(id);
    return outputStandard.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "", description = "", tags = "outputStandards", security = @SecurityRequirement(name = "token_auth"))
  @DeleteMapping("/outputStandards/{id}")
  public ResponseEntity<?> deleteOutputStandard(@PathVariable Long id) {
    outputStandardService.delete(id);
    return ResponseEntity.ok("OK");
  }

  @Operation(summary = "", description = "", tags = "levels", security = @SecurityRequirement(name = "token_auth"))
  // level
  @PostMapping("/levels")
  public ResponseEntity<Level> createLevel(@RequestBody Level level) {
    if (level.getId() != null) {
      throw new ResourceBadRequestException("A new Level cannot already have an ID");
    }
    Level result = levelService.save(level);
    return ResponseEntity.ok(result);
  }

  @Operation(summary = "", description = "", tags = "levels", security = @SecurityRequirement(name = "token_auth"))
  @PutMapping("/levels/{id}")
  public ResponseEntity<Level> updateLevel(@PathVariable(value = "id", required = false) final Long id, @RequestBody Level level) {
    if (level.getId() == null) {
      throw new ResourceBadRequestException("id null");
    }
    if (!Objects.equals(id, level.getId())) {
      throw new ResourceBadRequestException("id invalid");
    }

    if (!levelRepository.existsById(id)) {
      throw new ResourceBadRequestException("Entity not found id ");
    }

    Optional<Level> result = levelService.update(level);

    return result.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "", description = "", tags = "levels", security = @SecurityRequirement(name = "token_auth"))
  @GetMapping("/levels")
  public ResponseEntity<List<Level>> getAllLevels() {
    List<Level> list = levelService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @Operation(summary = "", description = "", tags = "levels", security = @SecurityRequirement(name = "token_auth"))
  @GetMapping("/levels/{id}")
  public ResponseEntity<Level> getLevel(@PathVariable Long id) {
    Optional<Level> level = levelService.findOne(id);
    return level.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "", description = "", tags = "levels", security = @SecurityRequirement(name = "token_auth"))
  @DeleteMapping("/levels/{id}")
  public ResponseEntity<?> deleteLevel(@PathVariable Long id) {
    levelService.delete(id);
    return ResponseEntity.ok("OK");
  }

  @Operation(summary = "", description = "", tags = "deliverys", security = @SecurityRequirement(name = "token_auth"))
  // delivery
  @PostMapping("/deliverys")
  public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
    if (delivery.getId() != null) {
      throw new ResourceBadRequestException("A new Delivery cannot already have an ID");
    }
    Delivery result = deliveryService.save(delivery);
    return ResponseEntity.ok(result);
  }

  @Operation(summary = "", description = "", tags = "deliverys", security = @SecurityRequirement(name = "token_auth"))
  @PutMapping("/deliverys/{id}")
  public ResponseEntity<Delivery> updateDelivery(@PathVariable(value = "id", required = false) final Long id, @RequestBody Delivery delivery) {
    if (delivery.getId() == null) {
      throw new ResourceBadRequestException("id null");
    }
    if (!Objects.equals(id, delivery.getId())) {
      throw new ResourceBadRequestException("id invalid");
    }

    if (!deliveryRepository.existsById(id)) {
      throw new ResourceBadRequestException("Entity not found id ");
    }

    Optional<Delivery> result = deliveryService.update(delivery);

    return result.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "", description = "", tags = "deliverys", security = @SecurityRequirement(name = "token_auth"))
  @GetMapping("/deliverys")
  public ResponseEntity<List<Delivery>> getAllDeliverys() {
    List<Delivery> list = deliveryService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @Operation(summary = "", description = "", tags = "deliverys", security = @SecurityRequirement(name = "token_auth"))
  @GetMapping("/deliverys/{id}")
  public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
    Optional<Delivery> delivery = deliveryService.findOne(id);
    return delivery.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "", description = "", tags = "deliverys", security = @SecurityRequirement(name = "token_auth"))
  @DeleteMapping("/deliverys/{id}")
  public ResponseEntity<?> deleteDelivery(@PathVariable Long id) {
    deliveryService.delete(id);
    return ResponseEntity.ok("OK");
  }

  @Operation(summary = "", description = "createdBy.code,asc", tags = "syllabuses", security = @SecurityRequirement(name = "token_auth"))
  @GetMapping("/syllabuses")
  @PreAuthorize("!hasAuthority('Syllabus_AccessDenied')")
  public ResponseEntity<List<SyllabusListDto>> getAllSyllabuses(
    @org.springdoc.api.annotations.ParameterObject Pageable pageable,
    @RequestParam(required = false) String[] keywords,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant[] createDate,
    Authentication authentication
  ) {
    return ResponseEntity.ok(
      syllabusService.findAll(SyllabusRepository.searchByKeywordsOrBycreateDates(keywords, createDate, authentication), pageable).getContent()
    );
  }

  @Operation(summary = "", description = "", tags = "syllabuses", security = @SecurityRequirement(name = "token_auth"))
  @GetMapping("/syllabuses/{id}")
  public ResponseEntity<Syllabus> getSyllabus(@PathVariable Long id) {
    Optional<Syllabus> result = syllabusService.findOne(id);
    return result.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "", description = "", tags = "syllabuses", security = @SecurityRequirement(name = "token_auth"))
  @DeleteMapping("/syllabuses/{id}")
  public ResponseEntity<?> deleteSyllabus(@PathVariable Long id) {
    syllabusService.delete(id);
    return ResponseEntity.ok("OK");
  }

  @Operation(summary = "", description = "", tags = "syllabuses", security = @SecurityRequirement(name = "token_auth"))
  // delivery
  @PostMapping("/syllabuses")
  public ResponseEntity<Syllabus> createSyllabus(@RequestBody Syllabus syllabus) {
    if (syllabus.getId() != null) {
      throw new ResourceBadRequestException("A new Syllabus cannot already have an ID");
    }
    Syllabus result = syllabusService.save(syllabus);
    return ResponseEntity.ok(result);
  }

  @Operation(summary = "", description = "", tags = "syllabuses", security = @SecurityRequirement(name = "token_auth"))
  @PutMapping("/syllabuses/{id}")
  public ResponseEntity<Syllabus> updateSyllabus(@PathVariable(value = "id", required = false) final Long id, @RequestBody Syllabus syllabus) {
    if (syllabus.getId() == null) {
      throw new ResourceBadRequestException("id null");
    }
    if (!Objects.equals(id, syllabus.getId())) {
      throw new ResourceBadRequestException("id invalid");
    }

    if (!syllabusRepository.existsById(id)) {
      throw new ResourceBadRequestException("Entity not found id ");
    }

    Optional<Syllabus> result = syllabusService.update(syllabus);

    return result.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "", description = "", tags = "syllabuses", security = @SecurityRequirement(name = "token_auth"))
  @PostMapping("/syllabuses/duplicate/{id}")
  public ResponseEntity<?> duplicateSyllabuses(@PathVariable Long id) {
    Syllabus syllabus = syllabusRepository.findById(id).orElseThrow(() -> new ResourceBadRequestException("syllabus id not found!"));

    return null;
  }
}

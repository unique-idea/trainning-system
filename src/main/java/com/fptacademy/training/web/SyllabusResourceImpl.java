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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.HeaderUtil;
import org.apache.tomcat.util.http.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

  // OutputStandards
  @PostMapping("/OutputStandards")
  public ResponseEntity<OutputStandard> createOutputStandard(@RequestBody OutputStandard OutputStandardDTO) {
    if (OutputStandardDTO.getId() != null) {
      throw new ResourceBadRequestException("A new OutputStandard cannot already have an ID");
    }
    OutputStandard result = outputStandardService.save(OutputStandardDTO);
    return ResponseEntity.ok(result);
  }

  @PutMapping(value = "/OutputStandards/{id}")
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

  @GetMapping("/OutputStandards")
  public ResponseEntity<List<OutputStandard>> getAllOutputStandards() {
    List<OutputStandard> list = outputStandardService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/OutputStandards/{id}")
  public ResponseEntity<OutputStandard> getOutputStandard(@PathVariable Long id) {
    Optional<OutputStandard> outputStandard = outputStandardService.findOne(id);
    return outputStandard.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/OutputStandards/{id}")
  public ResponseEntity<?> deleteOutputStandard(@PathVariable Long id) {
    outputStandardService.delete(id);
    return ResponseEntity.ok("OK");
  }

  // level
  @PostMapping("/levels")
  public ResponseEntity<Level> createLevel(@RequestBody Level level) {
    if (level.getId() != null) {
      throw new ResourceBadRequestException("A new Level cannot already have an ID");
    }
    Level result = levelService.save(level);
    return ResponseEntity.ok(result);
  }

  @PutMapping(value = "/levels/{id}")
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

  @GetMapping("/levels")
  public ResponseEntity<List<Level>> getAllLevels() {
    List<Level> list = levelService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/levels/{id}")
  public ResponseEntity<Level> getLevel(@PathVariable Long id) {
    Optional<Level> level = levelService.findOne(id);
    return level.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/levels/{id}")
  public ResponseEntity<?> deleteLevel(@PathVariable Long id) {
    levelService.delete(id);
    return ResponseEntity.ok("OK");
  }

  // delivery
  @PostMapping("/deliverys")
  public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
    if (delivery.getId() != null) {
      throw new ResourceBadRequestException("A new Delivery cannot already have an ID");
    }
    Delivery result = deliveryService.save(delivery);
    return ResponseEntity.ok(result);
  }

  @PutMapping(value = "/deliverys/{id}")
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

  @GetMapping("/deliverys")
  public ResponseEntity<List<Delivery>> getAllDeliverys() {
    List<Delivery> list = deliveryService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping("/deliverys/{id}")
  public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
    Optional<Delivery> delivery = deliveryService.findOne(id);
    return delivery.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/deliverys/{id}")
  public ResponseEntity<?> deleteDelivery(@PathVariable Long id) {
    deliveryService.delete(id);
    return ResponseEntity.ok("OK");
  }

  @GetMapping("/syllabuses")
  public ResponseEntity<List<SyllabusListDto>> getAllSyllabuses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
    Page<SyllabusListDto> page = syllabusService.findAll(pageable);
    return ResponseEntity.ok(page.getContent());
  }
}

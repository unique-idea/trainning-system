package com.fptacademy.training.web;

import com.fptacademy.training.domain.Assessment;
import com.fptacademy.training.domain.Delivery;
import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Material;
import com.fptacademy.training.domain.OutputStandard;
import com.fptacademy.training.domain.Session;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.domain.Unit;
import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.repository.DeliveryRepository;
import com.fptacademy.training.repository.LevelRepository;
import com.fptacademy.training.repository.OutputStandardRepository;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.DeliveryService;
import com.fptacademy.training.service.LevelService;
import com.fptacademy.training.service.OutputStandardService;
import com.fptacademy.training.service.SyllabusService;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusDetailDto;
import com.fptacademy.training.service.dto.SyllabusDto.SyllabusListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
  
  /**
     * Output Standard
     */
    //region OutputStandard
    @Operation(
            summary = "Add new output standard",
            description = "OutputStandard Input: name(string)",
            tags = "Output standard",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = OutputStandard.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"H₂SO₄\"}")
                    )),
                    @ApiResponse(description = "The length of name must be in [1 - 10]", responseCode = "400", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = OutputStandard.class), examples = @ExampleObject(
                            name = "Example", value = "{\"name\": \"H₂SO₄HNO₃H₃PO₄H₂O\"}"))),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "This name is already exists", responseCode = "500", content = @Content)
            }
    )
    @PostMapping("/outputStandards")
  public ResponseEntity<OutputStandard> createOutputStandard(@RequestBody OutputStandard OutputStandardDTO) {
    if (OutputStandardDTO.getId() != null) {
      throw new ResourceBadRequestException("A new OutputStandard cannot already have an ID");
    }
    OutputStandard result = outputStandardService.save(OutputStandardDTO);
    return ResponseEntity.ok(result);
  }

  @Operation(
            summary = "Update output standard by id",
            description = "OutputStandard Input: id(long), name(string)",
            tags = "Output standard",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = OutputStandard.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"H₂SO₄\"}"))),
                    @ApiResponse(description = "The length of name must be in [1 - 10]", responseCode = "400", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = OutputStandard.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"H₂SO₄HNO₃H₃PO₄H₂O\"}"))),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "This name is already exists", responseCode = "500", content = @Content)
            }
    )
  @PutMapping(value = "/outputStandards/{id}")
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

  @Operation(
            summary = "List all output standards",
            description = "OutputStandard Input: null",
            tags = "Output standard",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = OutputStandard.class), examples = @ExampleObject(
                            name = "Example", value = "[{\"id\": 1, \"name\": \"H₂SO₄\"}" +
                            ", {\"id\": 2, \"name\": \"HNO₃\"}" +
                            ", {\"id\": 3, \"name\": \"H₃PO₄\"}" +
                            ", {\"id\": 4, \"name\": \"H₂O\"}]")
                    )),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content)
            }
    )
  @GetMapping("/outputStandards")
  public ResponseEntity<List<OutputStandard>> getAllOutputStandards() {
    List<OutputStandard> list = outputStandardService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @Operation(
            summary = "Get an output standard by id",
            description = "OutputStandard Input: id(long)",
            tags = "Output standard",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = OutputStandard.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"H₂SO₄\"}")
                    )),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content)
            }
    )
  @GetMapping("/outputStandards/{id}")
  public ResponseEntity<OutputStandard> getOutputStandard(@PathVariable Long id) {
    Optional<OutputStandard> outputStandard = outputStandardService.findOne(id);
    return outputStandard.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(
            summary = "Delete an output standard by id",
            description = "OutputStandard Input: id(long)",
            tags = "Output standard",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content)
            }
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
            summary = "Add new level",
            description = "Level Input: name(string)",
            tags = "Level",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Level.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"H₂SO₄\"}")
                    )),
                    @ApiResponse(description = "The length of name must be in [1 - 50]", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "This name is already exists", responseCode = "500", content = @Content)
            }
    )
  @PostMapping("/levels")
  public ResponseEntity<Level> createLevel(@RequestBody Level level) {
    if (level.getId() != null) {
      throw new ResourceBadRequestException("A new Level cannot already have an ID");
    }
    Level result = levelService.save(level);
    return ResponseEntity.ok(result);
  }

  @Operation(
            summary = "Update a level by id",
            description = "Level Input: id(long), name(string)",
            tags = "Level",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Level.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"H₂SO₄\"}")
                    )),
                    @ApiResponse(description = "The length of name must be in [1 - 50]", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "This name is already exists", responseCode = "500", content = @Content)
            }
    )
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

  @Operation(
            summary = "List all levels",
            description = "Level Input: null",
            tags = "Level",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Level.class), examples = @ExampleObject(
                            name = "Example", value = "[{\"id\": 1, \"name\": \"H₂SO₄\"}" +
                            ", {\"id\": 2, \"name\": \"HNO₃\"}" +
                            ", {\"id\": 3, \"name\": \"H₃PO₄\"}" +
                            ", {\"id\": 4, \"name\": \"H₂O\"}]")
                    )),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content)
            }
    )
  @GetMapping("/levels")
  public ResponseEntity<List<Level>> getAllLevels() {
    List<Level> list = levelService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @Operation(
            summary = "Show a level by id",
            description = "Level Input: id(long)",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Level.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"H₂SO₄\"}")
                    )),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content)
            }
    )
  @GetMapping("/levels/{id}")
  public ResponseEntity<Level> getLevel(@PathVariable Long id) {
    Optional<Level> level = levelService.findOne(id);
    return level.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(
            summary = "Delete level by id",
            description = "Level Input: id(long)",
            tags = "Level",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content)
            }
    )
  @DeleteMapping("/levels/{id}")
  public ResponseEntity<?> deleteLevel(@PathVariable Long id) {
    levelService.delete(id);
    return ResponseEntity.ok("OK");
  }
  //endregion

  /**
     * Delivery
     */
     //region Delivery
  @Operation(
            summary = "Add new delivery",
            description = "Delivery Input: name(string)",
            tags = "Delivery",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Assessment.class), examples = @ExampleObject(
                            name = "Example", value = "[{\"id\": 1, \"name\": \"Assignment/Lab\"}" +
                                    ", {\"id\": 2, \"name\": \"Concept/Lecture\"}" +
                                    ", {\"id\": 3, \"name\": \"Guide/Review\"}" +
                                    ", {\"id\": 4, \"name\": \"Test/Quiz\"}" +
                                    ", {\"id\": 5, \"name\": \"Exam\"}" +
                                    ", {\"id\": 6, \"name\": \"Seminar/Workshop\"}]"))),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "The number is out of the range", responseCode = "400", content = @Content)
            }
    )
  @PostMapping("/deliveries")
  public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
    if (delivery.getId() != null) {
      throw new ResourceBadRequestException("A new Delivery cannot already have an ID");
    }
    Delivery result = deliveryService.save(delivery);
    return ResponseEntity.ok(result);
  }

  @Operation(
            summary = "Update a delivery by id",
            description = "Delivery Input: id(long), name(string)",
            tags = "Delivery",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Assessment.class), examples = @ExampleObject(
                            name = "Example", value = "{\"id\": 1, \"name\": \"ABCD/TEST\"}"))),
                    @ApiResponse(description = "Not found", responseCode = "404"),
                    @ApiResponse(description = "The number is out of the range", responseCode = "400")
            }
    )
  @PutMapping(value = "/deliveries/{id}")
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

  @Operation(
            summary = "List all deliveries",
            description = "Delivery Input: null",
            tags = "Delivery",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @GetMapping("/deliveries")
  public ResponseEntity<List<Delivery>> getAllDeliveries() {
    List<Delivery> list = deliveryService.findAll();
    return ResponseEntity.ok().body(list);
  }

  @Operation(
            summary = "Show a delivery by id",
            description = "Delivery Input: id(long)",
            tags = "Delivery",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @GetMapping("/deliveries/{id}")
  public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
    Optional<Delivery> delivery = deliveryService.findOne(id);
    return delivery.map(response -> ResponseEntity.ok().body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(
            summary = "Delete a delivery by id",
            description = "Delivery Input: id(long)",
            tags = "Delivery",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @DeleteMapping("/deliveries/{id}")
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
            summary = "List all syllabuses",
            description = "Syllabus Input: Panaging, Search by keywords and date" +
                ", Sort by keywords",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @GetMapping(value = "/syllabuses")
  @PreAuthorize("!hasAuthority('Syllabus_AccessDenied')")
  public ResponseEntity<Page<SyllabusListDto>> getAllSyllabuses(
    @org.springdoc.api.annotations.ParameterObject Pageable pageable,
    @RequestParam(required = false) String[] keywords,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant[] createDate,
    Authentication authentication
  ) {
    return ResponseEntity.ok(
      syllabusService.findAll(SyllabusRepository.searchByKeywordsOrBycreateDates(keywords, createDate, authentication), pageable)
    );
  }

  @Operation(
            summary = "Show syllabus by id",
            description = "Syllabus Input: id(long)",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @GetMapping("/syllabuses/{id}")
  public ResponseEntity<SyllabusDetailDto> getSyllabus(@PathVariable Long id) {
    return syllabusService
      .findOne(id)
      .map(response -> ResponseEntity.ok().body(response))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(
            summary = "Delete syllabus by id",
            description = "Syllabus Input: id(long)",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @DeleteMapping("/syllabuses/{id}")
  public ResponseEntity<?> deleteSyllabus(@PathVariable Long id) {
    Syllabus syl = syllabusRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    syl.setStatus(SyllabusStatus.REJECTED);
    syllabusService.delete(syl);
    return ResponseEntity.ok("OK");
  }

  @Operation(
            summary = "De-active syllabus by id",
            description = "Syllabus Input: id(long)",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @PutMapping("/syllabuses/de-active/{id}")
  public ResponseEntity<?> deActiveSyllabus(@PathVariable Long id) {
    Syllabus syllabus = syllabusRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    syllabus.setStatus(SyllabusStatus.DEACTIVATED);
    syllabusService.delete(syllabus);
    return ResponseEntity.ok("OK");
  }

  @Operation(
            summary = "Active syllabus by id",
            description = "Syllabus Input: id(long)",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @PutMapping("/syllabuses/active/{id}")
  public ResponseEntity<?> activeSyllabus(@PathVariable Long id) {
    Syllabus syl = syllabusRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    syl.setStatus(SyllabusStatus.ACTIVATED);
    syllabusService.delete(syl);
    return ResponseEntity.ok("OK");
  }

  @Operation(
            summary = "Add new syllabus",
            description = "Syllabus Input: name(string), attendeeNumber, status, duration" +
                "technicalRequirement, courseObjective, trainingPrinciple, level, session" +
                "unit, lesson, formatType, delivery, materials, assessment",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = Syllabus.class), examples = @ExampleObject(
                            name = "Example", value =
                            "{\n" +
                            "  \"name\": \"TEST\",\n" +
                            "  \"code\": \"\",\n" +
                            "  \"attendeeNumber\": 10,\n" +
                            "  \"status\": \"DRAFT\",\n" +
                            "  \"duration\": 0,\n" +
                            "  \"version\": 0,\n" +
                            "  \"technicalRequirement\": \"testing testing...\",\n" +
                            "  \"courseObjective\": \"write something in here\",\n" +
                            "  \"trainingPrinciple\": \"marvelous!\",\n" +
                            "  \"level\": {\n" +
                            "    \"name\": \"normal\"\n" +
                            "  },\n" +
                            "  \"sessions\": [\n" +
                            "    {\n" +
                            "      \"index\": 1,\n" +
                            "      \"name\": \"Test\",\n" +
                            "      \"units\": [\n" +
                            "        {\n" +
                            "          \"title\": \".Test\",\n" +
                            "          \"name\": \".Test\",\n" +
                            "          \"index\": 1,\n" +
                            "          \"totalDurationLesson\": 0,\n" +
                            "          \"lessons\": [\n" +
                            "            {\n" +
                            "              \"name\": \"test\",\n" +
                            "              \"duration\": 30,\n" +
                            "              \"outputStandard\": {\n" +
                            "                \"name\": \"OJT\"\n" +
                            "              },\n" +
                            "              \"formatType\": {\n" +
                            "                \"name\": \"Online\"\n" +
                            "              },\n" +
                            "              \"delivery\": {\n" +
                            "                \"name\": \"Exam\"\n" +
                            "              },\n" +
                            "              \"materials\": [\n" +
                            "                {\n" +
                            "                  \"name\": \"Material name\",\n" +
                            "                  \"fileUrl\": \"https://noName.com\"\n" +
                            "                }\n" +
                            "              ]\n" +
                            "            }\n" +
                            "          ]\n" +
                            "        }\n" +
                            "      ]\n" +
                            "    }\n" +
                            "  ],\n" +
                            "  \"assessment\": {\n" +
                            "    \"quiz\": 10,\n" +
                            "    \"assignment\": 20,\n" +
                            "    \"finalField\": 70,\n" +
                            "    \"finalTheory\": 40,\n" +
                            "    \"finalPractice\": 60,\n" +
                            "    \"gpa\": 70\n" +
                            "  }\n" +
                            "}")
                    )),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            }
    )
  @PostMapping("/syllabuses")
  public ResponseEntity<SyllabusDetailDto> createSyllabus(@RequestBody SyllabusDetailDto syllabus) {
    return ResponseEntity.ok(syllabusService.save(syllabus));
  }

  @Operation(
            summary = "Update syllabus",
            description = "Syllabus Input: name(string), attendeeNumber, status, duration" +
                "technicalRequirement, courseObjective, trainingPrinciple, level, session" +
                "unit, lesson, formatType, delivery, materials, assessment",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @PutMapping("/syllabuses")
  public ResponseEntity<SyllabusDetailDto> updateSyllabus(@RequestBody SyllabusDetailDto syllabus) {
    if (!syllabusRepository.existsById(syllabus.getId())) {
      throw new ResourceBadRequestException("Entity not found id ");
    }
    return syllabusService
      .update(syllabus)
      .map(response -> ResponseEntity.ok().body(response))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Operation(
            summary = "Duplicate syllabus",
            description = "Syllabus Input: id(long)",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @PostMapping("/syllabuses/duplicate/{id}")
  @Transactional
  public ResponseEntity<Syllabus> duplicateSyllabus(@PathVariable Long id) {
    ModelMapper map = new ModelMapper();
    Syllabus syllabus = syllabusRepository.findById(id).orElseThrow(() -> new ResourceBadRequestException("syllabus id not found!"));
    map
      .createTypeMap(Syllabus.class, Syllabus.class)
      .addMappings(mapper -> {
        mapper.skip(Syllabus::setId);
        mapper
          .using((Converter<String, String>) ctx -> ctx.getSource().contains("duplicate") ? ctx.getSource() : ctx.getSource() + " (duplicate)")
          .map(Syllabus::getName, Syllabus::setName);
        mapper.map(src -> Long.toString(UUID.randomUUID().getMostSignificantBits() & 0xffffff, 36).toUpperCase(), Syllabus::setCode);
      });
    map.createTypeMap(Assessment.class, Assessment.class).addMappings(mapper -> mapper.skip(Assessment::setId));
    map.createTypeMap(Session.class, Session.class).addMappings(mapper -> mapper.skip(Session::setId));
    map.createTypeMap(Unit.class, Unit.class).addMappings(mapper -> mapper.skip(Unit::setId));
    map.createTypeMap(Lesson.class, Lesson.class).addMappings(mapper -> mapper.skip(Lesson::setId));
    map.createTypeMap(Material.class, Material.class).addMappings(mapper -> mapper.skip(Material::setId));
    return ResponseEntity.ok(syllabusRepository.save(map.map(syllabus, Syllabus.class)));
  }

  @Operation(
            summary = "Get template syllabus",
            description = "Syllabus Input: null",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @GetMapping(value = "/syllabuses/template", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<Resource> getTemplateSyllabus() {
    Resource resource = new ClassPathResource("templates/Syllabus-template.xlsx");
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
    headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
    return ResponseEntity.ok().headers(headers).body(resource);
  }

  @Operation(
            summary = "Import syllabus",
            description = "Syllabus Input: file, scanning, handle",
            tags = "Syllabus",
            security = @SecurityRequirement(name = "token_auth"),
            responses = {
                    @ApiResponse(description = "Success | OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
  @PostMapping(value = "/syllabuses/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> importSyllabus(
    @RequestPart(value = "file", required = true) MultipartFile file,
    @Schema(description = "code or name", type = "array") @RequestParam(required = false) String[] scanning,
    @Schema(allowableValues = { "allow", "replace", "skip" }) @RequestParam(required = true) String handle
  ) {
    return ResponseEntity.ok(syllabusService.importExcel(file, scanning, handle));
  }
  //endregion
}

package com.fptacademy.training.web.api;

import com.fptacademy.training.domain.ClassDetail;
import com.fptacademy.training.repository.ClassDetailRepository;
import com.fptacademy.training.service.dto.ClassDetailDto;
import com.fptacademy.training.service.mapper.ClassDetailMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/api/class_detail")
public interface ClassDetailResource {


    @GetMapping("/{class_id}")
    public Optional<ClassDetailDto> getDetailsByClassId(@PathVariable String class_id);
    
}

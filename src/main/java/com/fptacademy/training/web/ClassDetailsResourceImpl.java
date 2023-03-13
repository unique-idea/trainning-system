package com.fptacademy.training.web;

import com.fptacademy.training.domain.ClassDetail;
import com.fptacademy.training.repository.ClassDetailRepository;
import com.fptacademy.training.service.dto.ClassDetailDto;
import com.fptacademy.training.service.mapper.ClassDetailMapper;
import com.fptacademy.training.web.api.ClassDetailResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ClassDetailsResourceImpl implements ClassDetailResource {

    private final ClassDetailRepository repository;
    private final ClassDetailMapper classDetailMapper;



    @Override
    public Optional<ClassDetailDto> getDetailsByClassId(@PathVariable String class_id){
        Optional<ClassDetail> details = repository.findDetailsByClass_Id(class_id);
        return details.map(classDetailMapper::toDto);
    }
}

package com.fptacademy.training.service;

import com.fptacademy.training.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClassService {

    private final ClassRepository classRepository;

}

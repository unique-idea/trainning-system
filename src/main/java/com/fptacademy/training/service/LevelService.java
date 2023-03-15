package com.fptacademy.training.service;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class LevelService {
    private final LevelRepository levelRepository;

    public Level getLevelByName(String name) {
        return levelRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Level " + name + " not found"));
    }

    public boolean checkLevelIsExist(String name){
        Optional<Level> level =  levelRepository.findByName(name);
        return level.isEmpty();
    }
}

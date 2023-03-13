package com.fptacademy.training.service;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.repository.LevelRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LevelService {

  private final LevelRepository levelRepository;
  private final ModelMapper modelMapper;

  public Level save(Level level) {
    return levelRepository.save(level);
  }

  public Optional<Level> update(Level level) {
    return levelRepository
      .findById(level.getId())
      .map(ops -> {
        modelMapper.map(level, ops);
        return ops;
      })
      .map(levelRepository::save);
  }

  @Transactional(readOnly = true)
  public List<Level> findAll() {
    return levelRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Level> findOne(Long id) {
    return levelRepository.findById(id);
  }

<<<<<<< HEAD
  public void delete(Long id) {
    levelRepository.deleteById(id);
  }
=======
        return findOne(level.getId()).map(ops -> {
                    modelMapper.map(level, ops);
                    return ops;
                })
                .map(levelRepository::save);
    }

    @Transactional(readOnly = true)
    public Optional<Level> findOne(Long id) {
        return levelRepository.findById(id);
    }

    public List<Level> getAllLevel() {
        return levelRepository.findAll();
    }

    public Optional<Level> getLevelByID(Long id) {
        return levelRepository.findById(id);
    }

    public List<Level> getLevelByName(String name) {
        return levelRepository.findByNameContains(name);
    }

    public void delete(Long id) {
        levelRepository.deleteById(id);
    }
>>>>>>> parent of c7e9f5f (11.29 13.03.2023)
}

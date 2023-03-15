package com.fptacademy.training.service;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.service.dto.RoleDto;
import com.fptacademy.training.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role getRoleByName(String name){
       return roleRepository
               .findByName(name)
               .orElseThrow(() -> new ResourceNotFoundException("Role " + name + " not found"));
    }

    public Role getRoleByID (long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type role " + id + " not found"));
    }
    public List<RoleDto> getAllPermission(){
        return roleMapper.toDtos(roleRepository.findAll());
    }

}

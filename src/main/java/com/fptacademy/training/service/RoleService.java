package com.fptacademy.training.service;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.enumeration.RoleName;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.service.dto.RoleDto;
import com.fptacademy.training.service.mapper.RoleMapper;
import com.fptacademy.training.web.vm.RoleVM;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {
    @Autowired
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role getRoleByName(String name) {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role " + name + " not found"));
    }

    public Role getRoleByID(long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type role " + id + " not found"));
    }

    public List<RoleDto> getAllPermission() {
        return roleMapper.toDtos(roleRepository.findAll());
    }

    public List<RoleDto> updatePermission(List<RoleVM> role) {
        for (int i = 0; i < role.size(); i++) {
            if (role.get(i).permissions().size() == 5) {
                if (role.get(i).name().equalsIgnoreCase(RoleName.SUPER_ADMIN.toString())) {
                    List<String> listPermission = role.get(i).permissions().stream().toList();
                    updatePermission(RoleName.SUPER_ADMIN.toString(), listPermission);
                } else if (role.get(i).name().equalsIgnoreCase(RoleName.CLASS_ADMIN.toString())) {
                    List<String> listPermission = role.get(i).permissions().stream().toList();
                    updatePermission(RoleName.CLASS_ADMIN.toString(), listPermission);
                } else if (role.get(i).name().equalsIgnoreCase(RoleName.TRAINER.toString())) {
                    List<String> listPermission = role.get(i).permissions().stream().toList();
                    updatePermission(RoleName.TRAINER.toString(), listPermission);
                } else if (role.get(i).name().equalsIgnoreCase(RoleName.TRAINEE.toString())) {
                    List<String> listPermission = role.get(i).permissions().stream().toList();
                    updatePermission(RoleName.TRAINEE.toString(), listPermission);
                } else {
                    throw new ResourceNotFoundException("Do no have this role: " + role.get(i).name());
                }
//            } else {
//                throw new ResourceBadRequestException("Permission must have value of Syllabus, Program, Class, Material, User");
            }
        }
        return roleMapper.toDtos(roleRepository.findAll());
    }

    public void updatePermission(String role, List<String> permissions) {
        Role roleEntity = roleRepository
                .findByName(role)
                .orElseThrow(() -> new ResourceNotFoundException("Role " + role + " not found"));
        ;
        if (roleEntity != null) {
            roleEntity.setPermissions(permissions);
            roleRepository.save(roleEntity);
        }
    }
}

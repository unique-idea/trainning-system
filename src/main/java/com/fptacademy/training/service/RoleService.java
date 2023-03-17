package com.fptacademy.training.service;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.enumeration.RoleName;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.service.dto.RoleDto;
import com.fptacademy.training.service.mapper.RoleMapper;
import com.fptacademy.training.web.vm.RoleVM;
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
    public List<RoleDto> getAllPermission() {
        return roleMapper.toDtos(roleRepository.findAll());
    }


    }

    public List<Role> updatePermission(List<RoleVM> role) {

        for (int i = 0; i < role.size(); i++) {
            if (role.get(i).permissions().size() == 5) {
                String permission = String.join(",", role.get(i).permissions());
                if (role.get(i).name().equalsIgnoreCase(RoleName.SUPER_ADMIN.toString())) {
                    roleRepository.updatePermission(RoleName.SUPER_ADMIN.toString(), permission);
                } else if (role.get(i).name().equalsIgnoreCase(RoleName.CLASS_ADMIN.toString())) {
                    roleRepository.updatePermission(RoleName.CLASS_ADMIN.toString(), permission);
                } else if (role.get(i).name().equalsIgnoreCase(RoleName.TRAINER.toString())) {
                    roleRepository.updatePermission(RoleName.TRAINER.toString(), permission);
                } else if (role.get(i).name().equalsIgnoreCase(RoleName.TRAINEE.toString())) {
                    roleRepository.updatePermission(RoleName.TRAINEE.toString(), permission);
                System.out.println(permission);
                if(role.get(i).name().equalsIgnoreCase(RoleName.SUPER_ADMIN.toString())){
                    roleRepository.updatePermission(RoleName.SUPER_ADMIN.toString(), role.get(i).permissions());
                } else if(role.get(i).name().equalsIgnoreCase(RoleName.CLASS_ADMIN.toString())){
                    roleRepository.updatePermission(RoleName.CLASS_ADMIN.toString(), role.get(i).permissions());
                } else if(role.get(i).name().equalsIgnoreCase(RoleName.TRAINER.toString())){
                    roleRepository.updatePermission(RoleName.TRAINER.toString(), role.get(i).permissions());
                } else if(role.get(i).name().equalsIgnoreCase(RoleName.TRAINEE.toString())){
                    roleRepository.updatePermission(RoleName.TRAINEE.toString(), role.get(i).permissions());
                } else {
                    throw new ResourceNotFoundException("Do no have this role: " + role.get(i).name());
                }
            }

        }
        return roleRepository.findAll();
    }

}

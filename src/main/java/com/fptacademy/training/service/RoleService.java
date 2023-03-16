package com.fptacademy.training.service;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.enumeration.RoleName;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.web.vm.RoleVM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

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

    public List<Role> getAllPermission() {
        return roleRepository.findAll();
    }

    public List<Role> updatePermission(List<RoleVM> role) {
        for(int i = 0 ; i < role.size(); i++){
            if(role.get(i).permissions().size() == 5){
                String permission = String.join(",", role.get(i).permissions());
                if(role.get(i).name().equalsIgnoreCase(RoleName.SUPER_ADMIN.toString())){
                    roleRepository.updatePermission(RoleName.SUPER_ADMIN.toString(), permission);
                } else if(role.get(i).name().equalsIgnoreCase(RoleName.CLASS_ADMIN.toString())){
                    roleRepository.updatePermission(RoleName.CLASS_ADMIN.toString(), permission);
                } else if(role.get(i).name().equalsIgnoreCase(RoleName.TRAINER.toString())){
                    roleRepository.updatePermission(RoleName.TRAINER.toString(), permission);
                } else if(role.get(i).name().equalsIgnoreCase(RoleName.TRAINEE.toString())){
                    roleRepository.updatePermission(RoleName.TRAINEE.toString(), permission);
                } else {
                    throw new ResourceNotFoundException("Do no have this role: " + role.get(i).name());
                }
            }

        }
        return getAllPermission();
    }


}

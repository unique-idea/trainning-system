package com.fptacademy.training.service;

import com.fptacademy.training.domain.User;
import com.fptacademy.training.exception.ResourceAlreadyExistsException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.service.mapper.UserMapper;
import com.fptacademy.training.web.vm.UserVM;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final LevelService levelService;

    private final UserMapper userMapper;

    public UserDto createUser(UserVM userVM){
        if(userRepository.existsByEmail(userVM.email())){
            throw new ResourceAlreadyExistsException("User with email " + userVM.email() + " already existed");
        }
        User user =userMapper.toEntity(userVM, levelService, roleService);
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getUsers() {
        return userMapper.toDtos(userRepository.findAll());
    }

    public Optional<UserDto> findUserByEmail(String email) {
        return Optional.ofNullable(userMapper.toDto(userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"))));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Something went wrong, can not get current logged in user"));
    }

    public User getUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    public Collection<? extends GrantedAuthority> getUserPermissionsByEmail(String email) {
        return getUserByEmail(email)
                .getRole()
                .getPermissions()
                .stream().map(SimpleGrantedAuthority::new).toList();
    }
}

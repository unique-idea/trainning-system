package com.fptacademy.training.service;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.exception.ResourceAlreadyExistsException;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.service.mapper.UserMapper;
import com.fptacademy.training.service.util.ExcelUploadService;
import com.fptacademy.training.web.vm.UserVM;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final LevelService levelService;

    private final UserMapper userMapper;

    private final ExcelUploadService excelUploadService;

    private final String[] userProperties = {"id", "fullName", "email", "phone", "address", "birthday", "level", "role", "status"};

    public UserDto createUser(UserVM userVM) {
        if (userRepository.existsByEmail(userVM.email())) {
            throw new ResourceAlreadyExistsException("User with email " + userVM.email() + " already existed");
        }
        User user = userMapper.toEntity(userVM, levelService, roleService);
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getUsers(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id");
        return userMapper.toDtos(userRepository.findAll(pages).getContent());
    }

    public Optional<UserDto> findUserByEmail(String email) {
        return Optional.ofNullable(userMapper.toDto(userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"))));
    }

    public UserDto deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            return userMapper.toDto(user);
        } else {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }

    public List<UserDto> findUserByName(String name) {
        List<UserDto> userDto = userMapper.toDtos(userRepository.findByFullNameContaining(name));
        if (userDto.isEmpty()) {
            throw new ResourceNotFoundException("User with name " + name + " not found");
        }
        return userDto;
    }

    public void changeRole(long id, String typeRole) {
        Role role = roleService.getRoleByName(typeRole);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User does not exist");
        } else if (user.get().getRole().getId() == 1) {
            throw new ResourceNotFoundException("This user can't change role");
        }
        user.get().setRole(role);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Something went wrong, can not get current logged in user"));
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

    public void saveUsersToDB(MultipartFile file) {
        if (ExcelUploadService.isValidExcelFile(file)) {
            try {
                List<User> users = excelUploadService.getUserDataFromExcel(file.getInputStream());
                this.userRepository.saveAll(users);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    private Boolean isPropertyValid(String property) {
        for (String userProperty : userProperties) {
            if (userProperty.equals(property)) {
                return true;
            }
        }
        return false;
    }

    private LocalDate parseDate(String date) {
        LocalDate localDate = null;
        if (date != null)
            try {
                localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                throw new ResourceBadRequestException(
                        date + ": Date format is wrong. Please use yyyy-MM-dd format");
            }
        return localDate;
    }

    public List<UserDto> getUsersByFilters(String email, String fullName, String code,
            String levelName, String roleName, Boolean activated, String birthdayFrom, String birthdayTo,
            String status, String sort, Integer pageNumber, Integer pageSize) {
        LocalDate birthdayFromDate = parseDate(birthdayFrom);
        if (birthdayFromDate == null) birthdayFromDate = LocalDate.of(0, 1, 1);
        LocalDate birthdayToDate = parseDate(birthdayTo);
        if (birthdayToDate == null) birthdayToDate = LocalDate.of(9999, 12, 31);
        
        String sortProperty, sortDirection;
        if (sort == null) sort = "id,ASC";
        if (pageNumber == null) pageNumber = 0;
        if (pageSize == null) pageSize = 10;

        sortProperty = sort.split(",")[0];
        sortDirection = sort.split(",")[1].toUpperCase();

        if (!sortDirection.equals("ASC") && !sortDirection.equals("DESC"))
            throw new ResourceBadRequestException(sortDirection + ": Sort direction must be ASC or DESC");
        if (!isPropertyValid(sortProperty))
            throw new ResourceBadRequestException(sortProperty + ": Sort property is not valid");
        

        Direction direction = Direction.valueOf(sortDirection);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, direction, sortProperty);
        if (status != null)
            status.replace(" ", "_");
        
        Page<User> page = userRepository.findByFilters(email, fullName, code, levelName, roleName, activated, birthdayFromDate, birthdayToDate, status, pageable);
        return userMapper.toDtos(page.getContent());
    }
}

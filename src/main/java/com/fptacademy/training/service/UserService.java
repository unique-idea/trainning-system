package com.fptacademy.training.service;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.domain.enumeration.RoleName;
import com.fptacademy.training.domain.enumeration.UserStatus;
import com.fptacademy.training.exception.ResourceAlreadyExistsException;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.dto.ReturnPageDto;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.service.mapper.UserMapper;
import com.fptacademy.training.service.util.ExcelExportUtils;
import com.fptacademy.training.service.util.ExcelUploadService;
import com.fptacademy.training.web.vm.NoNullRequiredUserVM;
import com.fptacademy.training.web.vm.UserVM;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@SuppressWarnings("unused")
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final LevelService levelService;
    private final UserMapper userMapper;
    private final ExcelUploadService excelUploadService;

    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize maxFileSize;

    private String[] userProperties = {"id", "email", "fullName", "code", "level", "role", "activated", "birthday",
            "status"};

    public UserDto createUser(UserVM userVM) {
        if (userRepository.existsByEmail(userVM.email())) {
            throw new ResourceAlreadyExistsException("User with email " + userVM.email() + " already existed");
        }
        if (userRepository.existsByCode(userVM.code())) {
            throw new ResourceAlreadyExistsException("User with code " + userVM.code() + " already existed");
        }
        User user = userMapper.toEntity(userVM, levelService, roleService);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(userVM.code()));
        user.setGender(userVM.gender().equals("male"));
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getUsers(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "id");
        return userMapper.toDtos(userRepository.findUserByActivatedIsTrue(pages).getContent());
    }

    public Optional<UserDto> findUserByEmail(String email) {
        return Optional.ofNullable(userMapper.toDto(userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"))));
    }

    public UserDto deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        User user = optionalUser.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if (email.equals(user.getEmail())) {
            throw new ResourceBadRequestException("You cannot delete your own account");
        }
        user.setActivated(!user.getActivated());

        User updatedUser = userRepository.save(user);
        UserDto userDto = userMapper.toDto(updatedUser);
        return userDto;
    }

    public UserDto deActive(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        User user = optionalUser.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if (email.equals(user.getEmail()))
            throw new ResourceBadRequestException("You cannot de-active your own account");

        if (user.getStatus() == null)
            user.setStatus(UserStatus.INACTIVE);
        else if (user.getStatus() == UserStatus.INACTIVE) {
            if (user.getRole().equals("Class Admin") || user.getRole().equals("Super Admin"))
                user.setStatus(UserStatus.ACTIVE);
            else
                user.setStatus(UserStatus.ON_BOARDING);
        } else
            user.setStatus(UserStatus.INACTIVE);

        User updatedUser = userRepository.save(user);
        UserDto userDto = userMapper.toDto(updatedUser);
        return userDto;
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

    public User getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }
        throw new UsernameNotFoundException("Something went wrong, can not get current logged in user");
    }

    public User getUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
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

    public ReturnPageDto<List<UserDto>> getUsersByFilters(String email, String fullName, String code,
                                                          String levelName, String roleName, Boolean activated, String birthdayFrom, String birthdayTo,
                                                          String status, String sort, Integer pageNumber, Integer pageSize) {
        LocalDate birthdayFromDate = parseDate(birthdayFrom);
        if (birthdayFromDate == null)
            birthdayFromDate = LocalDate.of(0, 1, 1);
        LocalDate birthdayToDate = parseDate(birthdayTo);
        if (birthdayToDate == null)
            birthdayToDate = LocalDate.of(9999, 12, 31);

        String sortProperty, sortDirection;
        if (sort == null)
            sort = "id,ASC";
        if (pageNumber == null)
            pageNumber = 0;
        if (pageSize == null)
            pageSize = 10;

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

        Page<User> page = userRepository.findByFilters(email, fullName, code, levelName, roleName, activated,
                birthdayFromDate, birthdayToDate, status, pageable);

        return userMapper.toPageUserDto(page);
    }

    public List<UserDto> importUsersToDB(MultipartFile file) {
        List<User> users = null;
        try {
            if (ExcelUploadService.isValidExcelFile(file)) {
                users = excelUploadService.getUserDataFromExcel(file.getInputStream());
                this.userRepository.saveAll(users);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("The file is not a valid excel file");
        }
        return userMapper.toDtos(users);
    }

    public UserDto getUserById(Long id) {
        return userMapper.toDto(userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not existed!")));
    }

    public UserDto updateUserById(NoNullRequiredUserVM noNullRequiredUserVM, Long id) {
        if (noNullRequiredUserVM == null) {
            throw new ResourceBadRequestException("Invalid params");
        }
        Optional<User> checkUser = userRepository
                .findById(id)
                .map(
                        existUser -> {
                            if (noNullRequiredUserVM.fullName() != null) {
                                existUser.setFullName(noNullRequiredUserVM.fullName());
                            }
                            if (noNullRequiredUserVM.password() != null) {
                                existUser.setPassword(noNullRequiredUserVM.password());
                            }
                            if (noNullRequiredUserVM.email() != null) {
                                existUser.setEmail(noNullRequiredUserVM.email());
                            }
                            if (noNullRequiredUserVM.birthday() != null) {
                                existUser.setBirthday(LocalDate.parse(noNullRequiredUserVM.birthday()));
                            }
                            if (noNullRequiredUserVM.avatarUrl() != null) {
                                existUser.setAvatarUrl(noNullRequiredUserVM.avatarUrl());
                            }
                            if (noNullRequiredUserVM.level() != null) {
                                existUser.setLevel(levelService.getLevelByName(noNullRequiredUserVM.level()));
                            }
                            if (noNullRequiredUserVM.code() != null) {
                                existUser.setCode(noNullRequiredUserVM.code());
                            }
                            if (noNullRequiredUserVM.gender() != null) {
                                Boolean gender = true;
                                if (noNullRequiredUserVM.gender().equalsIgnoreCase("female")) {
                                    gender = false;
                                }
                                existUser.setGender(gender);
                            }
                            if (noNullRequiredUserVM.status() != null) {
                                existUser.setStatus(UserStatus.valueOf(noNullRequiredUserVM.status()
                                        .replace(" ", "_").toUpperCase(Locale.ROOT)));
                            }
                            if (noNullRequiredUserVM.activated() != null) {
                                Boolean activated = true;
                                if (noNullRequiredUserVM.activated().equalsIgnoreCase("false")) {
                                    activated = false;
                                }
                                existUser.setActivated(activated);
                            }
                            return existUser;
                        })
                .map(userRepository::save);

        return userMapper
                .toDto(checkUser.orElseThrow(() -> new ResourceNotFoundException("Can't update not existed user!")));
    }

    public Collection<? extends GrantedAuthority> getUserPermissionsByEmail(String email) {
        return getUserByEmail(email)
                .getRole()
                .getPermissions()
                .stream().map(SimpleGrantedAuthority::new).toList();
    }

    public void exportUsersToExcel(HttpServletResponse response) {
        List<User> users = userRepository.findAll();
        ExcelExportUtils excelExport = new ExcelExportUtils(users);
        excelExport.exportDataToExcel(response);
    }

    public Role getUserRoleByEmail(String email) {
        return getUserByEmail(email).getRole();
    }

    public List<User> getMemberOfClassByRole(Long classDetailId, RoleName roleName) {
        return userRepository.findMemberOfClassByRole(classDetailId, roleName.toString());
    }
}

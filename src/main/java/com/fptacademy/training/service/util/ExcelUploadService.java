package com.fptacademy.training.service.util;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.domain.enumeration.UserStatus;
import com.fptacademy.training.repository.LevelRepository;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.LevelService;
import com.fptacademy.training.service.RoleService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class ExcelUploadService {

    public final RoleService roleService;

    public final LevelService levelService;
    private final UserRepository userRepository;
    private final LevelRepository levelRepository;

    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<User> getUserDataFromExcel(InputStream inputStream) {
        List<User> users = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("users");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }

                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                User user = new User();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0: // full name
                            if (cell.getStringCellValue() != null) {
                                user.setFullName(cell.getStringCellValue());
                            }
                            break;

                        case 1: // email
                            if (cell.getStringCellValue() != null) {
                                user.setEmail(cell.getStringCellValue());
                            }
                            break;

                        case 2: // code
                            if (cell.getStringCellValue() != null) {
                                user.setCode(cell.getStringCellValue());
                            }
                            break;

                        case 3: // password
                            if (cell.getStringCellValue() != null) {
                                user.setPassword(cell.getStringCellValue());
                            }
                            break;

                        case 4: // gender
                            if (cell.getStringCellValue().equalsIgnoreCase("true")) {
                                user.setGender(Boolean.TRUE);
                            } else {
                                user.setGender(Boolean.FALSE);
                            }
                            break;

                        case 5: // role
                            if (cell.getStringCellValue() != null) {
                                Role role = roleService.getRoleByName(cell.getStringCellValue());
                                user.setRole(role);
                            }
                            break;

                        case 6: // Activated
                            if (cell.getStringCellValue().equalsIgnoreCase("true")) {
                                user.setActivated(Boolean.TRUE);
                            } else {
                                user.setActivated(Boolean.FALSE);
                            }
                            break;

                        case 7: //level
                            if (cell.getStringCellValue() != null) {
                                Level level = null;
                                boolean checkLevel = levelService.checkLevelIsExist(cell.getStringCellValue());
                                if (checkLevel) {
                                    level = new Level(cell.getStringCellValue());
                                } else {
                                    level = levelService.getLevelByName(cell.getStringCellValue());
                                }
                                levelRepository.save(level);
                                user.setLevel(level);
                            }
                            break;

                        case 8: //status
                            if (cell.getStringCellValue() != null) {
                                UserStatus status = UserStatus.valueOf(cell.getStringCellValue());
                                user.setStatus(status);
                            }
                            break;

                        case 9:
                            if (cell.getStringCellValue() != null) {
                                user.setAvatarUrl(cell.getStringCellValue());
                            }
                            break;

                        case 10: // birthday
                            if (cell.getDateCellValue() != null) {
                                user.setBirthday(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            }
                            break;

                        default:
                            break;
                    }
                    cellIndex++;
                }
                if (user.getActivated() != null && !userRepository.existsByCodeIgnoreCase(user.getCode())) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}

package com.fptacademy.training.service.util;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.domain.UserStatus;
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

    public RoleService roleService;

    public LevelService levelService;

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
                            user.setFullName(cell.getStringCellValue());
                            break;

                        case 1: // email
                            user.setEmail(cell.getStringCellValue());
                            break;

                        case 2: // code
                            user.setCode(cell.getStringCellValue());
                            break;

                        case 3: // password
                            user.setPassword(cell.getStringCellValue());
                            break;

                        case 4: // birthday
                            user.setBirthday(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                            break;

                        case 5: // gender
                            user.setGender(cell.getBooleanCellValue());
                            break;

                        case 6: // role
                            Role role = roleService.getRoleByName(cell.getStringCellValue());
                            user.setRole(role);
                            break;

                        case 7: // Activated
                            user.setActivated(cell.getBooleanCellValue());
                            break;

                        case 8: //level
                            Level level = levelService.getLevelByName(cell.getStringCellValue());
                            user.setLevel(level);
                            break;

                        case 9: //status
                            UserStatus status = UserStatus.valueOf(cell.getStringCellValue());
                            user.setStatus(status);
                            break;

                        case 10:
                            user.setAvatarUrl(cell.getStringCellValue());
                            break;

                        default:
                            break;
                    }
                    cellIndex++;
                }
                users.add(user);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return users;
    }
}

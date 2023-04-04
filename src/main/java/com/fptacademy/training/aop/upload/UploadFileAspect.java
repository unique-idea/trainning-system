package com.fptacademy.training.aop.upload;

import com.fptacademy.training.domain.FileStorage;
import com.fptacademy.training.service.FileStorageService;
import com.fptacademy.training.service.util.S3UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Aspect
public class UploadFileAspect {
    private final Environment env;
    @Autowired
    private S3UploadFileUtil s3UploadFileUtil;
    @Autowired
    private FileStorageService fileStorageService;

    @Pointcut("execution(public * com.fptacademy.training.service.ProgramService.importProgramFromExcel(..))")
    public void importProgramExcelPointcut(){}

    @After("importProgramExcelPointcut() && args(file,..)")
    public void afterImportProgramExcel(MultipartFile file) {
        String accessKey = env.getProperty("amazon.s3.accessKey");
        if (StringUtils.hasText(accessKey)) {
            String url = s3UploadFileUtil.handleFileUpload(file, "Program").toString();
            fileStorageService.addFile(new FileStorage(url, "Program"));
        }
    }
}

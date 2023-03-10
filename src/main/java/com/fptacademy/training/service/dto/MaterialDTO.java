package com.fptacademy.training.service.dto;

import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Data
public class MaterialDTO {

    private Long id;

    private String name;

    private String fileUrl;

    private Instant createdAt;


}

package com.fptacademy.training;

import com.fptacademy.training.domain.*;
import com.fptacademy.training.domain.Class;
import com.fptacademy.training.repository.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.time.LocalTime;

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(
        name = "token_auth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Swagger for FPT Academy Training System",
                description = "This is list of endpoints and documentations of REST API for FPT Academy Training System",
                version = "1.0"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server")
        },
        tags = {
                @Tag(name = "authentication", description = "REST API endpoints for authentication"),
                @Tag(name = "program", description = "REST API endpoints for training program")
        }
)
public class FaTrainingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaTrainingSystemApplication.class, args);
    }

   /* @Bean
    CommandLineRunner run(ClassRepository classRepository,
                          ClassDetailRepository classDetailRepository,
                          ClassScheduleRepository classScheduleRepository,
                          LocationRepository locationRepository,
                          UserRepository userRepository,
                          AttendeeRepository attendeeRepository) {

        return args -> {
            User user = userRepository.findById(3L).orElse(null);
            Attendee attendee = attendeeRepository.findById(2L).orElse(null);

            Location location = locationRepository.findById(1L).orElse(null);

            *//*Location location = new Location();
//            location.setId(1L);
            location.setCity("Ho Chi Minh");
            location.setFsu("Ftown1");
            locationRepository.save(location);

            location = new Location();
//            location.setId(2L);
            location.setCity("Ha Noi");
            location.setFsu("ABC");
            locationRepository.save(location);*//*

     *//*Attendee attendee = new Attendee();
            attendee.setType("Intern");
            attendeeRepository.save(attendee);

            attendee = new Attendee();
            attendee.setType("Fresher");
            attendeeRepository.save(attendee);

            attendee = new Attendee();
            attendee.setType("Online fee-fresher");
            attendeeRepository.save(attendee);

            attendee = new Attendee();
            attendee.setType("Offline fee-fresher");
            attendeeRepository.save(attendee);*//*


            Class classField = new Class();
//            classField.setId(1L);
            classField.setCreatedAt(Instant.now());
//            classField.setCreatedBy(user);
            classField.setName("Java Intern 01");
            classField.setCode("J01");
            classField.setDuration(4);
            classField = classRepository.save(classField);


            ClassDetail classDetail = new ClassDetail();
//            classDetail.setId(1L);
            classDetail.setClassField(classField);
            classDetail.setStatus("Active");
            classDetail.setLocation(locationRepository.findById(1L).orElse(null));
            classDetail.setAttendee(attendee);
            classDetail.setStartAt(LocalTime.of(8, 30, 0));
            classDetail.setFinishAt(LocalTime.of(10, 30, 0));
            classDetailRepository.save(classDetail);

        };
    }*/

}

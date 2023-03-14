package com.fptacademy.training.repository;

import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.domain.enumeration.SyllabusStatus;
import com.fptacademy.training.security.Permissions;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public interface SyllabusRepository extends JpaRepository<Syllabus, Long>, JpaSpecificationExecutor<Syllabus> {
  static Specification<Syllabus> searchByKeywordsOrBycreateDates(String[] keywords, Instant[] createDate, Authentication authentication) {
    return (root, query, builder) -> {
      query.distinct(true);
      Predicate predicatesStatus;
      Predicate[] predicates = keywords != null
        ? Arrays
          .stream(keywords)
          .flatMap(keyword ->
            Stream.of(
              builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
              builder.like(builder.lower(root.get("code")), "%" + keyword.toLowerCase() + "%"),
              builder.like(builder.lower(root.get("createdBy").get("code")), "%" + keyword.toLowerCase() + "%"),
              builder.like(
                builder.lower(
                  root
                    .join("sessions", JoinType.LEFT)
                    .join("units", JoinType.LEFT)
                    .join("lessons", JoinType.LEFT)
                    .join("outputStandard", JoinType.LEFT)
                    .get("name")
                ),
                "%" + keyword.toLowerCase() + "%"
              )
            )
          )
          .toArray(Predicate[]::new)
        : null;

      predicates =
        createDate != null
          ? predicates != null
            ? Stream
              .concat(
                Arrays.stream(predicates),
                Stream.of(
                  builder.between(
                    root.get("createdAt"),
                    Arrays.stream(createDate).min(Instant::compareTo).get(),
                    Arrays.stream(createDate).max(Instant::compareTo).get()
                  )
                )
              )
              .toArray(Predicate[]::new)
            : new Predicate[] {
              builder.between(
                root.get("createdAt"),
                Arrays.stream(createDate).min(Instant::compareTo).get(),
                Arrays.stream(createDate).max(Instant::compareTo).get()
              ),
            }
          : predicates;

      switch (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(r -> r.contains("Syllabus_")).findFirst().get()) {
        case Permissions.SYLLABUS_MODIFY:
          predicatesStatus =
            builder.in(root.get("status")).value(Arrays.asList(SyllabusStatus.DRAFT, SyllabusStatus.ACTIVATED, SyllabusStatus.DEACTIVATED));
          break;
        case Permissions.SYLLABUS_CREATE:
          predicatesStatus =
            builder.in(root.get("status")).value(Arrays.asList(SyllabusStatus.DRAFT, SyllabusStatus.ACTIVATED, SyllabusStatus.DEACTIVATED));
          break;
        case Permissions.SYLLABUS_VIEW:
          predicatesStatus = builder.in(root.get("status")).value(Arrays.asList(SyllabusStatus.ACTIVATED));
          break;
        case Permissions.SYLLABUS_FULL_ACCESS:
          predicatesStatus =
            builder
              .in(root.get("status"))
              .value(Arrays.asList(SyllabusStatus.ACTIVATED, SyllabusStatus.DEACTIVATED, SyllabusStatus.DRAFT, SyllabusStatus.REJECTED));
          break;
        default:
          predicatesStatus =
            builder
              .in(root.get("status"))
              .value(Arrays.asList(SyllabusStatus.ACTIVATED, SyllabusStatus.DEACTIVATED, SyllabusStatus.DRAFT, SyllabusStatus.REJECTED));
          break;
      }

      return predicates != null ? builder.and(builder.or(predicates), predicatesStatus) : predicatesStatus;
    };
  }
}

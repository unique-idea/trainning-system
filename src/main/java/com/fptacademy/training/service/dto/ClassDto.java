package com.fptacademy.training.service.dto;
import java.io.Serializable;
import java.util.Date;



public class ClassDto implements Serializable{
    private static final Long serialVersionUID = 1L;
    private Long id;
    private Date created_at;
    private Date last_modified;
    private String code;
    private Integer duration;
    private String name;
    private Integer created_by;
    private Integer last_modified_by;
    private Integer program_id;
    public ClassDto() {
    }
    public ClassDto(Long id, Date created_at, Date last_modified, String code, Integer duration, String name,
            Integer created_by, Integer last_modified_by, Integer program_id) {
        this.id = id;
        this.created_at = created_at;
        this.last_modified = last_modified;
        this.code = code;
        this.duration = duration;
        this.name = name;
        this.created_by = created_by;
        this.last_modified_by = last_modified_by;
        this.program_id = program_id;
    }
    public static Long getSerialversionuid() {
        return serialVersionUID;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    public Date getLast_modified() {
        return last_modified;
    }
    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getCreated_by() {
        return created_by;
    }
    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }
    public Integer getLast_modified_by() {
        return last_modified_by;
    }
    public void setLast_modified_by(Integer last_modified_by) {
        this.last_modified_by = last_modified_by;
    }
    public Integer getProgram_id() {
        return program_id;
    }
    public void setProgram_id(Integer program_id) {
        this.program_id = program_id;
    }
    

}

package com.example.course.dto;

import java.time.LocalDate;

public class SemesterDto {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public SemesterDto() {}

    // Convenience constructor used by tests (id + name)
    public SemesterDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SemesterDto(Long id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

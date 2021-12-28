package com.dvc.artstyle.controller.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class ArtstylePostDto {
    @NotEmpty(message="Title cannot be empty or null")
    @Size(min=6, max=45, message = "Title must be between 6 and 45 symbols")
    private final String title;

    private final String summary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final String age;

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getAge() {
        return age;
    }
}

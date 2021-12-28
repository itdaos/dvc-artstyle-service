package com.dvc.artstyle.service;

import com.dvc.artstyle.model.jpa.Artstyle;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service("artstyleService")
public interface ArtstyleService {
    List<Artstyle> fetchAllArtstyles();
    Artstyle fetchArtstyleById(long id) throws IllegalArgumentException;
    Artstyle fetchArtstyleByName(String title) throws IllegalArgumentException;
    long createArtstyle(String maybeTitle, Optional<String> summary, Optional<Date> age) throws IllegalArgumentException;
    void deleteArtstyleById(long id) throws IllegalArgumentException;
    void updateArtstyle(long id, Optional<String> maybeTitle, Optional<String> summary, Optional<Date> age) throws IllegalArgumentException;
}

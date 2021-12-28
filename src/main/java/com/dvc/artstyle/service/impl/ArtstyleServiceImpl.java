package com.dvc.artstyle.service.impl;

import com.dvc.artstyle.model.ArtstyleRepository;
import com.dvc.artstyle.model.jpa.Artstyle;
import com.dvc.artstyle.service.ArtstyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ArtstyleServiceImpl implements ArtstyleService
{

    private final ArtstyleRepository artstyleRepo;

    @Override
    public List<Artstyle> fetchAllArtstyles() {
        return artstyleRepo.findAll();
    }

    @Override
    public Artstyle fetchArtstyleById(long id) throws IllegalArgumentException {
        final Optional<Artstyle> maybeArtstyle = artstyleRepo.findById(id);

        if (maybeArtstyle.isPresent()) {
            return maybeArtstyle.get();
        } else {
            throw new IllegalArgumentException("The artstyle with such id was not found");
        }
    }

    @Override
    public Artstyle fetchArtstyleByName(String title) throws IllegalArgumentException {
        final Optional<Artstyle> maybeArtstyle = artstyleRepo.findByTitle(title);

        if (maybeArtstyle.isPresent()) {
            return maybeArtstyle.get();
        } else {
            throw new IllegalArgumentException("The artstyle with such title was not found");
        }
    }

    @Override
    public long createArtstyle(
            String title,
            Optional<String> maybeSummary,
            Optional<Date> maybeAge
    ) throws IllegalArgumentException {
        final Artstyle artstyle = new Artstyle().setTitle(title);

        maybeAge.ifPresent(artstyle::setAge);
        maybeSummary.ifPresent(artstyle::setSummary);

        final Optional<Artstyle> maybeArtstyle = artstyleRepo.findByTitle(artstyle.getTitle());

        if (maybeArtstyle.isPresent()) throw new IllegalArgumentException("The artstyle with such title already exists");

        final Artstyle savedArtstyle = artstyleRepo.save(artstyle);
        return savedArtstyle.getId();
    }

    @Override
    public void deleteArtstyleById(long id) throws IllegalArgumentException {
        Optional<Artstyle> maybeArtstyle = artstyleRepo.findById(id);
        if (maybeArtstyle.isEmpty()) throw new IllegalArgumentException("The artstyle with such id is not present");

        artstyleRepo.deleteById(id);
    }

    @Override
    public void updateArtstyle(
            long id,
            Optional<String> maybeTitle,
            Optional<String> maybeSummary,
            Optional<Date> maybeAge
    ) throws IllegalArgumentException {
        Optional<Artstyle> maybeArtstyle = artstyleRepo.findById(id);

        if (maybeArtstyle.isEmpty()) throw new IllegalArgumentException("The author with such id was not found");

        final Artstyle artstyle = maybeArtstyle.get();

        maybeTitle.ifPresent(artstyle::setTitle);
        maybeAge.ifPresent(artstyle::setAge);
        maybeSummary.ifPresent(artstyle::setSummary);

        artstyleRepo.save(artstyle);
    }
}
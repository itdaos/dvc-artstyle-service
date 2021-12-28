package com.dvc.artstyle.controller;

import com.dvc.artstyle.controller.dto.ArtstylePatchDto;
import com.dvc.artstyle.controller.dto.ArtstylePostDto;
import com.dvc.artstyle.model.jpa.Artstyle;
import com.dvc.artstyle.service.impl.ArtstyleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/artstyles")
@RestController
public class ArtstyleController {

    private final ArtstyleServiceImpl artstyleService;

    @GetMapping
    public ResponseEntity<List<Artstyle>> index() {
        final List<Artstyle> artstyles = artstyleService.fetchAllArtstyles();

        return ResponseEntity.ok(artstyles);
    }

    @GetMapping("/{id}")
    public ResponseEntity showById(@PathVariable long id) {
        try {
            final Artstyle artstyle = artstyleService.fetchArtstyleById(id);

            return ResponseEntity.ok(artstyle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity showByName(@RequestParam String title) {
        try {
            final Artstyle artstyle = artstyleService.fetchArtstyleByName(title);
            return ResponseEntity.ok(artstyle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ArtstylePostDto artstyle) {

        final String title = artstyle.getTitle();
        final Optional<String> summary = Optional.ofNullable(artstyle.getSummary());
        final Optional<Date> age = Optional.ofNullable(artstyle.getAge()).map(Date::valueOf);

        try {
            final long createdArtstyleId = artstyleService.createArtstyle(title, summary, age);
            final String artstyleUri = String.format("/artstyles/%d", createdArtstyleId);

            return ResponseEntity.created(URI.create(artstyleUri)).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @Valid @RequestBody ArtstylePatchDto artstyle) {

        final Optional<String> title = Optional.ofNullable(artstyle.getTitle());
        final Optional<String> summary = Optional.ofNullable(artstyle.getSummary());
        final Optional<Date> age = Optional.ofNullable(artstyle.getAge()).map(Date::valueOf);

        try {
            final Artstyle createdArtstyleId = artstyleService.fetchArtstyleById(id);
            artstyleService.updateArtstyle(id, title, summary, age);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        try {
            artstyleService.deleteArtstyleById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}

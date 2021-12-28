package com.dvc.artstyle.model;

import com.dvc.artstyle.model.jpa.Artstyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtstyleRepository extends JpaRepository<Artstyle, Long> {

    @Query(value = "SELECT * FROM artstyles WHERE title = ?1", nativeQuery = true)
    Optional<Artstyle> findByTitle(String title);
}
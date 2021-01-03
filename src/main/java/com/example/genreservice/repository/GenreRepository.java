package com.example.genreservice.repository;

import com.example.genreservice.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findGenresByNameContaining(String name);
    List<Genre> findGenreByAbbreviationContaining(String abbreviation);
    Genre findGenreByName(String name);
}

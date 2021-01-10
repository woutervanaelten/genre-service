package com.example.genreservice.controller;

import com.example.genreservice.model.Genre;
import com.example.genreservice.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @PostConstruct
    public void fillDB(){
        genreRepository.deleteAll();
        if(genreRepository.count()==0) {
            genreRepository.save(new Genre("Action","Ac"));
            genreRepository.save(new Genre("Adventure","Ad"));
            genreRepository.save(new Genre("Drama","Dr"));
            genreRepository.save(new Genre("Crime","Cr"));
            genreRepository.save(new Genre("Sci-Fi","Sc"));
            genreRepository.save(new Genre("Biography","Bi"));
            genreRepository.save(new Genre("History","Hi"));
        }
        System.out.println("Genre test: " + genreRepository.findGenresByNameContaining("Action"));
    }

    @GetMapping("/genres/all")
    public List<Genre> getGenres(){
        return genreRepository.findAll();
    }

    @GetMapping("/genres/name/{name}")
    public List<Genre> getGenresByName(@PathVariable String name){
        return genreRepository.findGenresByNameContaining(name);
    }

    @GetMapping("/genres/abbreviation/{abbreviation}")
    public List<Genre> getGenresByAbbreviation(@PathVariable String abbreviation){
        return genreRepository.findGenreByAbbreviationContaining(abbreviation);
    }

    @GetMapping("/genres/genre/{name}")
    public Genre getGenreByName(@PathVariable String name){
        return genreRepository.findGenreByName(name);
    }

}

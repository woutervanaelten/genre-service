package com.example.genreservice;

import com.example.genreservice.model.Genre;
import com.example.genreservice.repository.GenreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    private Genre genre1 = new Genre("Action", "Ac");
    private Genre genre2 = new Genre("Adventure", "Ad");

    @BeforeEach
    public void beforeAllTests() {
        genreRepository.deleteAll();
        genreRepository.save(genre1);
        genreRepository.save(genre2);
    }

    @AfterEach
    public void afterAllTests() {
        genreRepository.deleteAll();
    }

    @Test
    public void givenGenres_whenGetGenresByName_thenReturnJsonGenres() throws Exception {
        mockMvc.perform(get("/genres/name/{name}", "Action"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Action")))
                .andExpect(jsonPath("$[0].abbreviation", is("Ac")));
    }

    @Test
    public void givenGenres_whenGetGenresByAbbreviation_thenReturnJsonGenres() throws Exception {
        mockMvc.perform(get("/genres/abbreviation/{abbreviation}", "A"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Action")))
                .andExpect(jsonPath("$[0].abbreviation", is("Ac")))
                .andExpect(jsonPath("$[1].name", is("Adventure")))
                .andExpect(jsonPath("$[1].abbreviation", is("Ad")));
    }

    @Test
    public void givenGenre_whenGetGenreByName_thenReturnJsonGenre() throws Exception {
        mockMvc.perform(get("/genres/genre/{name}", "Action"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Action")))
                .andExpect(jsonPath("$.abbreviation", is("Ac")));
    }

}

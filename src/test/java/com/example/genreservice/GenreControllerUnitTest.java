package com.example.genreservice;

import com.example.genreservice.model.Genre;
import com.example.genreservice.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    public void whenGetGenres_thenReturnJsonGenres() throws Exception {
        Genre genre1 = new Genre("Action", "Ac");
        Genre genre2 = new Genre("Adventure", "Ad");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre1);
        genreList.add(genre2);
        given(genreRepository.findAll()).willReturn(genreList);
        mockMvc.perform(get("/genres/all", "Action"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Action")))
                .andExpect(jsonPath("$[0].abbreviation", is("Ac")))
                .andExpect(jsonPath("$[1].name", is("Adventure")))
                .andExpect(jsonPath("$[1].abbreviation", is("Ad")));
    }

    @Test
    public void givenGenres_whenGetGenresByName_thenReturnJsonGenres() throws Exception {
        Genre genre = new Genre("Action", "Ac");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre);
        given(genreRepository.findGenresByNameContaining("Action")).willReturn(genreList);
        mockMvc.perform(get("/genres/name/{name}", "Action"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Action")))
                .andExpect(jsonPath("$[0].abbreviation", is("Ac")));
    }

    @Test
    public void givenGenres_whenGetGenresByAbbreviation_thenReturnJsonGenres() throws Exception {
        Genre genre1 = new Genre("Action", "Ac");
        Genre genre2 = new Genre("Adventure", "Ad");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre1);
        genreList.add(genre2);
        given(genreRepository.findGenreByAbbreviationContaining("A")).willReturn(genreList);
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
        Genre genre = new Genre("Action", "Ac");
        given(genreRepository.findGenreByName("Action")).willReturn(genre);
        mockMvc.perform(get("/genres/genre/{name}", "Action"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Action")))
                .andExpect(jsonPath("$.abbreviation", is("Ac")));
    }


}

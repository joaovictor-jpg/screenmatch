package br.com.jota.screenmetch.screenmetch.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URI;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo, @JsonAlias("totalSeasons") Integer totalTemporada,
                         @JsonAlias("imdbRating") String avaliacao, @JsonAlias("Poster") URI poster,
                         @JsonAlias("Plot") String sinopse, @JsonAlias("Actors") String atores,
                         @JsonAlias("Genre") String genero) {
}

package br.com.jota.screenmetch.screenmetch.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Title") String titulo, @JsonAlias("Season") Integer temporada,
                             @JsonAlias("totalSeasons") Integer totalDeEpisiodios,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodios) {
}

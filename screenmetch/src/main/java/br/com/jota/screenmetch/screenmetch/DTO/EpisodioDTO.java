package br.com.jota.screenmetch.screenmetch.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;

public record EpisodioDTO(Integer temporada, Integer numeroEpisodio, String titulo) {
}

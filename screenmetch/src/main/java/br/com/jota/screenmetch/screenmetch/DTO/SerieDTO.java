package br.com.jota.screenmetch.screenmetch.DTO;

import br.com.jota.screenmetch.screenmetch.models.enums.Categoria;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.net.URI;

public record SerieDTO(Long id, String titulo, Integer totalTemporada, Double avaliacao, URI poster,
                       String sinopse, String atores, Categoria genero) {
}

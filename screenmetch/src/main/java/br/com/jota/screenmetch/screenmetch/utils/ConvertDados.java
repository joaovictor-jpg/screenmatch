package br.com.jota.screenmetch.screenmetch.utils;

import br.com.jota.screenmetch.screenmetch.DTO.SerieDTO;
import br.com.jota.screenmetch.screenmetch.models.Serie;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertDados {
    public static List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporada(), s.getAvaliacao(),
                        s.getPoster(), s.getSinopse(), s.getAtores(), s.getGenero())).collect(Collectors.toList());
    }

    public static SerieDTO converteDados(Serie serie) {
        return new SerieDTO(serie.getId(), serie.getTitulo(), serie.getTotalTemporada(), serie.getAvaliacao(),
                serie.getPoster(), serie.getSinopse(), serie.getAtores(), serie.getGenero());
    }
}

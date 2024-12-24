package br.com.jota.screenmetch.screenmetch.controllers;

import br.com.jota.screenmetch.screenmetch.DTO.EpisodioDTO;
import br.com.jota.screenmetch.screenmetch.DTO.SerieDTO;
import br.com.jota.screenmetch.screenmetch.services.SerieServices;
import br.com.jota.screenmetch.screenmetch.utils.ConvertDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    SerieServices serieServices;

    @GetMapping()
    public List<SerieDTO> obterSeries() {
        return ConvertDados.converteDados(serieServices.listaSerieBuscadas());
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return ConvertDados.converteDados(serieServices.obterTop5Serie());
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obtreLancamento() {
        return ConvertDados.converteDados(serieServices.obterLancamento());
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
        return ConvertDados.converteDados(serieServices.obterPorId(id));
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
        return ConvertDados.converteDadosEpisodios(serieServices.ObterTodasTemporadas(id));
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obterEpisodiosPorTemporada(@PathVariable Long id, @PathVariable Long numeroTemporada) {
        return ConvertDados.converteDadosEpisodios(serieServices.obterEpisodioPorTemporada(id, numeroTemporada));
    }

    @GetMapping("/categorias/{categoria}")
    public List<SerieDTO> obterSeriePorCateoria(@PathVariable String categoria) {
        return ConvertDados.converteDados(serieServices.bucarSeriePorCategoria(categoria));
    }

    @GetMapping("/{id}/temporadas/top5episodios")
    public List<EpisodioDTO> obterTop5Episodios(@PathVariable Long id) {
        return  ConvertDados.converteDadosEpisodios(serieServices.top5Episodios(id));
    }

}

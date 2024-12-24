package br.com.jota.screenmetch.screenmetch.services;

import br.com.jota.screenmetch.screenmetch.DTO.EpisodioDTO;
import br.com.jota.screenmetch.screenmetch.models.Episodio;
import br.com.jota.screenmetch.screenmetch.models.Serie;
import br.com.jota.screenmetch.screenmetch.models.enums.Categoria;
import br.com.jota.screenmetch.screenmetch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieServices {
    @Autowired
    private SerieRepository repository;
    private List<Serie> series = new ArrayList<>();

    public List<Serie> listaSerieBuscadas() {
        this.series = repository.findAll();
        return series.stream()
                .sorted(Comparator.comparing(Serie::getGenero)).collect(Collectors.toList());
    }

    public List<Serie> obterTop5Serie() {
        return repository.findTop5ByOrderByAvaliacaoDesc();
    }

    public List<Serie> obterLancamento() {
        return repository.encontrarEpisodiosMaisRecentes();
    }

    public Serie obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);

        return serie.orElse(null);
    }

    public List<Episodio> ObterTodasTemporadas(Long id) {
        Optional<Serie> serieOptional = repository.findById(id);

        return serieOptional.map(Serie::getEpisodios).orElse(null);

    }

    public List<Episodio> obterEpisodioPorTemporada(Long id, Long numeroTemporada) {
        Optional<Serie> serieOptional = repository.findById(id);

        return repository.findSerieAndTemporada(serieOptional.get(), numeroTemporada);
    }

    public List<Serie> bucarSeriePorCategoria(String categoria) {
        Categoria categoria1 = Categoria.fromStringPtBr(categoria);
        return repository.findByGenero(categoria1).stream()
                .sorted(Comparator.comparing(Serie::getAvaliacao)).collect(Collectors.toList());
    }

    public List<Episodio> top5Episodios(Long id) {

        Optional<Serie> serie = repository.findById(id);

        return serie.map(s -> repository.findTop5EpisodiosPorSerie(s).stream().sorted(
                Comparator.comparing(Episodio::getAvaliacao)).collect(Collectors.toList())).orElse(null);
    }
}

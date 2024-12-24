package br.com.jota.screenmetch.screenmetch.services;

import br.com.jota.screenmetch.screenmetch.models.Serie;
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
}

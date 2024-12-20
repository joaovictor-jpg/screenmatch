package br.com.jota.screenmetch.screenmetch.repository;

import br.com.jota.screenmetch.screenmetch.models.Serie;
import br.com.jota.screenmetch.screenmetch.models.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Serie findByTituloContainingIgnoreCase(String titulo);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String atores, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria1);
}

package br.com.jota.screenmetch.screenmetch.models;

import br.com.jota.screenmetch.screenmetch.models.dto.DadosSerie;
import br.com.jota.screenmetch.screenmetch.models.enums.Categoria;
import jakarta.persistence.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Column()
    private Integer totalTemporada;
    @Column()
    private Double avaliacao;
    @Column()
    private URI poster;
    @Column()
    private String sinopse;
    @Column()
    private String atores;
    @Column()
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie() {
    }

    public Serie(DadosSerie dadosSerie, String sinopse) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporada = dadosSerie.totalTemporada();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.poster = dadosSerie.poster();
        this.sinopse = sinopse;
        this.atores = dadosSerie.atores();
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporada() {
        return totalTemporada;
    }

    public void setTotalTemporada(Integer totalTemporada) {
        this.totalTemporada = totalTemporada;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public URI getPoster() {
        return poster;
    }

    public void setPoster(URI poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return "Serie {" +
                "genero=" + genero +
                ", Atores='" + atores + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", poster=" + poster +
                ", avaliacao=" + avaliacao +
                ", totalTemporada=" + totalTemporada +
                ", titulo='" + titulo + '\'' +
                ", episodios=" + episodios +
                '}';
    }
}

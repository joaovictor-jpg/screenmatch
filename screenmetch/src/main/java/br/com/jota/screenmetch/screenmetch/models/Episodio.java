package br.com.jota.screenmetch.screenmetch.models;

import br.com.jota.screenmetch.screenmetch.models.dto.DadosEpisodio;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity()
@Table(name = "episodios")
public class Episodio {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Column()
    private Integer temporada;
    @Column()
    private Integer numeroEpisodio;
    @Column()
    private Double avaliacao;
    @Column()
    private LocalDate dataLancamento;
    @ManyToOne()
    private Serie serie;

    public Episodio() {
    }

    public Episodio(Integer temporada, DadosEpisodio d) {
        this.titulo = d.titulo();
        this.temporada = temporada;
        this.numeroEpisodio = d.numero();
        try {
            this.avaliacao = Double.valueOf(d.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(d.dataDeLancamento());
        } catch (DateTimeParseException ex) {
            this.dataLancamento = null;
        }
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

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "Episodio = {" +
                "titulo='" + titulo + '\'' +
                ", temporada=" + temporada +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento +
                '}';
    }
}

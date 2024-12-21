package br.com.jota.screenmetch.screenmetch.principal;

import br.com.jota.screenmetch.screenmetch.models.Episodio;
import br.com.jota.screenmetch.screenmetch.models.Serie;
import br.com.jota.screenmetch.screenmetch.models.dto.DadosSerie;
import br.com.jota.screenmetch.screenmetch.models.dto.DadosTemporada;
import br.com.jota.screenmetch.screenmetch.models.enums.Categoria;
import br.com.jota.screenmetch.screenmetch.repository.SerieRepository;
import br.com.jota.screenmetch.screenmetch.services.ConsultaGemini;
import br.com.jota.screenmetch.screenmetch.services.ConverteDados;
import br.com.jota.screenmetch.screenmetch.services.ObterDadosServices;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private final String ENDERECO = "http://www.omdbapi.com/?apikey=";
    private String apiKey;
    private ConverteDados converteDados = new ConverteDados();
    private String token;
    private SerieRepository serieRepository;
    private List<Serie> series;

    public Principal(String apiKey, String token, SerieRepository serieRepository) {
        this.apiKey = apiKey;
        this.token = token;
        this.serieRepository = serieRepository;
    }

    public void start() {
        menu();
    }

    private void menu() {
        var opcoes = -1;
        while (opcoes != 0) {
            var menu = """
                        1 - Buscar série
                        2 - Lista série
                        3 - Buscar série por título
                        4 - Buscar série por ator
                        5 - Top 5 fovoritas séries
                        6 - Buscar Por categória
                        7 - Buscar Episósdio
                        8 - Buscar Episódio por trecho
                        0 - Sair
                    """;

            System.out.println(menu);
            opcoes = scanner.nextInt();
            scanner.nextLine();

            switch (opcoes) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    listaSerieBuscadas();
                    break;
                case 3:
                    buscarSeriePorTittulo();
                    break;
                case 4:
                    buscarSeriePorAutor();
                    break;
                case 5:
                    top5FavoritasSeries();
                    break;
                case 6:
                    buscarSeriePorCategoria();
                    break;
                case 7:
                    buscarEpisodioPorSerie();
                    break;
                case 8:
                    buscarEpisodioPorTrecho();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Qual é o trecho do episódio");
        var trechoEpisodio = scanner.nextLine();
        List<Episodio> episodios = serieRepository.episodioPorTrecho(trechoEpisodio);
        episodios.forEach(e ->
                System.out.printf("Série: %s, Título episódio: %s Temporada: %s - Avaliação: %s\n",
                        e.getSerie().getTitulo(), e.getTitulo(), e.getTemporada(), e.getAvaliacao()));
    }

    private void buscarEpisodioPorSerie() {
        listaSerieBuscadas();
        System.out.println("Entre com o nome da série");
        var nomeSerie = scanner.nextLine();
        Optional<Serie> serieOptional = this.series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if (serieOptional.isPresent()) {
            List<DadosTemporada> temporadas = new ArrayList<>();

            var serie = serieOptional.get();

            for (int i = 1; i < serie.getTotalTemporada(); i++) {
                var json = ObterDadosServices.obterDadosServices(
                        this.ENDERECO +
                                this.apiKey +
                                "&t=" +
                                serie.getTitulo().replace(" ", "+") +
                                "&Season=" + i
                );
                DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
                System.out.println(dadosTemporada);
                temporadas.add(dadosTemporada);
            }

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream().flatMap(t -> t.episodios()
                    .stream()
                    .map(e -> new Episodio(t.temporada(), e))
            ).collect(Collectors.toList());
            serie.setEpisodios(episodios);
            serieRepository.save(serie);
        } else {
            System.out.println("Série não encontrada!");
        }

    }

    private void buscarSeriePorCategoria() {
        System.out.println("Digite a categoria");
        var categoria = scanner.nextLine();
        Categoria categoria1 = Categoria.fromStringPtBr(categoria);
        var series = serieRepository.findByGenero(categoria1);
        series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }

    private void top5FavoritasSeries() {
        List<Serie> series = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        series.stream()
                .forEach(System.out::println);
    }

    private void buscarSeriePorAutor() {
        System.out.println("Digite o nome do ator");
        var nomeAtor = scanner.nextLine();
        System.out.println("Digite a Nota de avaliação");
        var nota = scanner.nextDouble();
        List<Serie> serie = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, nota);
        serie.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTittulo() {
        System.out.println("Entre com título da serie");
        var titulo = scanner.nextLine();
        Serie serie = serieRepository.findByTituloContainingIgnoreCase(titulo);
        System.out.println(serie);
    }

    private void listaSerieBuscadas() {
        this.series = serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }


    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = scanner.nextLine();
        var json = ObterDadosServices.obterDadosServices(ENDERECO + this.apiKey + "&t=" + nomeSerie.replace(" ", "+"));
        DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        var sinopse = ConsultaGemini.obterTraducao(dados.sinopse().trim(), this.token);
        serieRepository.save(new Serie(dados, sinopse));
        System.out.println(dados);
    }

}

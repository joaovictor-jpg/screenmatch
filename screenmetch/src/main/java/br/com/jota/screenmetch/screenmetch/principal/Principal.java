package br.com.jota.screenmetch.screenmetch.principal;

import br.com.jota.screenmetch.screenmetch.models.Serie;
import br.com.jota.screenmetch.screenmetch.models.dto.DadosSerie;
import br.com.jota.screenmetch.screenmetch.models.enums.Categoria;
import br.com.jota.screenmetch.screenmetch.repository.SerieRepository;
import br.com.jota.screenmetch.screenmetch.services.ConsultaGemini;
import br.com.jota.screenmetch.screenmetch.services.ConverteDados;
import br.com.jota.screenmetch.screenmetch.services.ObterDadosServices;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private final String ENDERECO = "http://www.omdbapi.com/?apikey=";
    private String apiKey;
    private ConverteDados converteDados = new ConverteDados();
    private String token;
    private SerieRepository serieRepository;
    private List<Serie> serie;

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
                        4 - Buscar série por autor
                        5 - Top 5 fovoritas séries
                        6 - Buscar Por categória
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
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
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
        this.serie = serieRepository.findAll();
        serie.stream()
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

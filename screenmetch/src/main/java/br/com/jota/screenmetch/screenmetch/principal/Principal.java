package br.com.jota.screenmetch.screenmetch.principal;

import br.com.jota.screenmetch.screenmetch.models.Serie;
import br.com.jota.screenmetch.screenmetch.models.dto.DadosSerie;
import br.com.jota.screenmetch.screenmetch.repository.SerieRepository;
import br.com.jota.screenmetch.screenmetch.services.ConsultaGemini;
import br.com.jota.screenmetch.screenmetch.services.ConverteDados;
import br.com.jota.screenmetch.screenmetch.services.ObterDadosServices;

import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private final String ENDERECO = "http://www.omdbapi.com/?apikey=";
    private String apiKey;
    private ConverteDados converteDados = new ConverteDados();
    private String token;
    private SerieRepository serieRepository;

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
                        0 - Sair
                    """;

            System.out.println(menu);
            opcoes = scanner.nextInt();
            scanner.nextLine();

            switch (opcoes) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
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
    }

}

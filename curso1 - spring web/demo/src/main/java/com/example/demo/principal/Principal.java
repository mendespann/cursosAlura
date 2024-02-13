package com.example.demo.principal;


import java.util.ArrayList;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.example.demo.model.DadosTemporada;
import com.example.demo.model.DadosEpisodio;
import com.example.demo.model.dadosSerie;
import com.example.demo.service.ConsomeAPI;
import com.example.demo.service.ConverteDados;
import com.example.demo.model.Episodio;

public class Principal {

  private Scanner leitura = new Scanner(System.in);
  private ConsomeAPI consumo = new ConsomeAPI();
  private ConverteDados conversor = new ConverteDados();

  private final String ENDERECO = "https://www.omdbapi.com/?t=";
  private final String API_KEY = "&apikey=6585022c";

  public void exibeMenu(){
        // Solicita ao usuário o nome da série para busca.
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        
        // Obtém os dados da série da API.
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        dadosSerie dados = conversor.obterDados(json, dadosSerie.class);
        System.out.println(dados);

        // Cria uma lista para armazenar as temporadas da série.
        List<DadosTemporada> temporadas = new ArrayList<>();

        // Obtém os dados de cada temporada da série da API.
        for (int i = 1; i<=dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") +"&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        // Imprime o título de cada episódio de cada temporada.
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        // Cria uma lista com os dados de todos os episódios de todas as temporadas.
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        // Cria uma lista com todos os episódios de todas as temporadas.
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        // Cria um mapa com a média das avaliações de cada temporada.
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        // Calcula estatísticas sobre as avaliações dos episódios.
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}
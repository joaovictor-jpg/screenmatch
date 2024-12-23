package br.com.jota.screenmetch.screenmetch.controllers;

import br.com.jota.screenmetch.screenmetch.DTO.SerieDTO;
import br.com.jota.screenmetch.screenmetch.services.SerieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    SerieServices serieServices;

    @GetMapping("/series")
    public List<SerieDTO> obterSeries() {
        return serieServices.listaSerieBuscadas().stream().map(s -> new SerieDTO(s.getId(), s.getTitulo(),
                s.getTotalTemporada(), s.getAvaliacao(), s.getPoster(), s.getSinopse(), s.getAtores(),
                s.getGenero())).collect(Collectors.toList());
    }

    @GetMapping("/helloworld")
    public String hellorworld() {
        return "Hello World";
    }
}

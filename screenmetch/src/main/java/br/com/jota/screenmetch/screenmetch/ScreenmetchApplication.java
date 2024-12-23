package br.com.jota.screenmetch.screenmetch;

import br.com.jota.screenmetch.screenmetch.principal.Principal;
import br.com.jota.screenmetch.screenmetch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmetchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScreenmetchApplication.class, args);
    }
}

package Geomarket.Evento_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoServiceApplication.class, args);
	}

}

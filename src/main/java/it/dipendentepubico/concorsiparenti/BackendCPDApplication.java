package it.dipendentepubico.concorsiparenti;

import it.dipendentepubico.concorsiparenti.spring.websocket.WebSocketProperties;
import it.dipendentepubico.concorsiparenti.usecase.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, WebSocketProperties.class})
public class BackendCPDApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendCPDApplication.class, args);
	}

}

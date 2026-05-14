package dev.yashverma.gatioms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableScheduling
@EnableAdminServer
public class GatiomsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatiomsApplication.class, args);
	}

}

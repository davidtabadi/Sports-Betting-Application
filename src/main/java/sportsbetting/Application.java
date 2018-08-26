package sportsbetting;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
// @EnableJpaRepositories("com.acme.repositories")
@SpringBootApplication
public class Application {

	@PostConstruct
	public void init() {
		System.err.println("Application Started ...");
	}

	@PreDestroy
	public void destroy() {
		System.err.println("Application Ended ...");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

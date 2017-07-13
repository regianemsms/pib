package br.org.piblimeira.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"br.org.piblimeira.domain"})
@EnableJpaRepositories(basePackages = {"br.org.piblimeira.repository"})
@EnableTransactionManagement
@ComponentScan("br.org.piblimeira.app")
public class PibLimeiraApplication {

    public static void main(String[] args) {
    	//por aqui que inicia o projeto
        SpringApplication.run(PibLimeiraApplication.class, args);
    }

}

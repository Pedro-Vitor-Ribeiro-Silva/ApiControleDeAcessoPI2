package com.example.controledeacesso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ControleDeAcessoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleDeAcessoApplication.class, args);
    }

}

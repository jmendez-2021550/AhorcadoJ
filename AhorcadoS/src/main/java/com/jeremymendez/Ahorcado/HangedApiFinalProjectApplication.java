package com.jeremymendez.Ahorcado;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HangedApiFinalProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HangedApiFinalProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("API del Ahorcado en Funcionamiento.");
    }
}

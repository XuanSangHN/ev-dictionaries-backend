package com.evdictionaries;

import com.evdictionaries.service.impl.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan("com.evdictionaries.models")
@EnableJpaRepositories("com.evdictionaries.repository")
public class Application implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
}

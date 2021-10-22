package com.example.pigLatiniser;

import com.example.pigLatiniser.domain.Phrase;
import com.example.pigLatiniser.repository.PigLatiniserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger((LoadDatabase.class));

    @Bean
    CommandLineRunner initDatabase(PigLatiniserRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(
                    new Phrase(
                    "nix on the stupid", "ixnay on the upidstay"
                    )
            ));
        };
    }
}

package com.example.pigLatiniser.web;

import com.example.pigLatiniser.PhraseModelAssembler;
import com.example.pigLatiniser.PhraseNotFoundException;
import com.example.pigLatiniser.domain.Phrase;
import com.example.pigLatiniser.repository.PigLatiniserRepository;
import com.example.pigLatiniser.service.PigLatiniserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class PigLatiniserController {

    private final PigLatiniserRepository repository;

    private final PhraseModelAssembler assembler;

    PigLatiniserController(PigLatiniserRepository repository, PhraseModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        String greeting = "Hello! Your friendly neighbourhood piggy here!";
        return ResponseEntity.ok(greeting);
    }

    @GetMapping("/phrases")
    public CollectionModel<EntityModel<Phrase>> all() {

        List <EntityModel<Phrase>> phrases = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(phrases,
                linkTo(methodOn(PigLatiniserController.class).all()).withSelfRel());
    }


    @GetMapping("/phrases/{id}")
    public EntityModel<Phrase> one(@PathVariable Integer id) {

        Phrase phrase = repository.findById(id)
                .orElseThrow(() -> new PhraseNotFoundException(id));

        return assembler.toModel(phrase);
    }

    @PostMapping(value ="/phrases",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Phrase> postBody(@RequestBody Phrase newPhrase){
        Phrase translation = PigLatiniserService.translate(newPhrase);
        Phrase persistedPhrase = repository.save(translation);
        return ResponseEntity
                .created(URI
                        .create(String.format("/api/phrases/%s", newPhrase.getId())))
                .body(persistedPhrase);
    }

    @DeleteMapping("/phrases/{id}")
    void deletePhrase(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}

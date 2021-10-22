package com.example.pigLatiniser;

import com.example.pigLatiniser.domain.Phrase;
import com.example.pigLatiniser.web.PigLatiniserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PhraseModelAssembler implements RepresentationModelAssembler<Phrase, EntityModel<Phrase>> {
    @Override
    public EntityModel<Phrase> toModel(Phrase phrase) {
        return EntityModel.of(phrase,
                linkTo(methodOn(PigLatiniserController.class).one(phrase.getId())).withSelfRel(),
                linkTo(methodOn(PigLatiniserController.class).all()).withRel("phrases"));
    }
}


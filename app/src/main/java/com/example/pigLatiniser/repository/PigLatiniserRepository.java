package com.example.pigLatiniser.repository;

import com.example.pigLatiniser.domain.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PigLatiniserRepository extends JpaRepository<Phrase, Integer> {
}

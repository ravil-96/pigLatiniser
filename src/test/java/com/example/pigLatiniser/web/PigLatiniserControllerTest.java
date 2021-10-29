package com.example.pigLatiniser.web;

import com.example.pigLatiniser.PhraseModelAssembler;
import com.example.pigLatiniser.PhraseNotFoundException;
import com.example.pigLatiniser.repository.PigLatiniserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PigLatiniserControllerTest {
    private final PigLatiniserRepository repository = mock(PigLatiniserRepository.class);

    private final PhraseModelAssembler assembler = mock(PhraseModelAssembler.class);

    private final PigLatiniserController controller = new PigLatiniserController(repository, assembler);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void shouldSendGreeting() throws Exception {
        this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello! Your friendly neighbourhood piggy here!")));
    }

    @Test
    public void shouldSendTranslatedPhrases() throws Exception {
        this.mockMvc.perform(get("/phrases")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("ixnay on the upidstay")));
    }

    @Test
    public void shouldFindAndSendTranslatedPhraseById() throws Exception {
        this.mockMvc.perform(get("/phrases/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("ixnay on the upidstay")));
    }

    @Test
    void exceptionTesting() {
        PhraseNotFoundException exception = assertThrows(
                PhraseNotFoundException.class,
                () -> { throw new PhraseNotFoundException(99); }
        );
        assertEquals("Could not find phrase 99", exception.getMessage());
    }

    @Test
    public void pigLatiniserShouldTranslateAndReturnPhrase() throws Exception {
        String testTranslation = "ixnay on the upidstay";
        mockMvc.perform(post("/phrases")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"originalPhrase\": \"nix on the stupid\"}")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.translatedPhrase").value(testTranslation));
    }

    @Test
    public void pigLatiniserShouldDeleteTranslatedPhraseById() throws Exception {
        this.mockMvc.perform(delete("/phrases/1")).andDo(print()).andExpect(status().isOk());
    }
}

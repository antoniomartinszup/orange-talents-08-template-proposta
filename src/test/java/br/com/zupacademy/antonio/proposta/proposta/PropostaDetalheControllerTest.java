package br.com.zupacademy.antonio.proposta.proposta;

import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PropostaDetalheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    Gson gson = new Gson();

    @BeforeEach
    void setup() {
        propostaRepository.deleteAll();
    }

    @Test
    @DisplayName("Proposta nao encontrada")
    public void propostaNaoEncontrada() throws Exception {

        mockMvc.perform(get("/propostas/{id}", 1000)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Busca Proposta com sucesso")
    void buscaPropostaComSucesso() throws Exception {
        Proposta proposta = new Proposta("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        proposta.setPropostaStatus(PropostaStatus.ELEGIVEL);
        propostaRepository.save(proposta);

        mockMvc.perform(get("/propostas/{id}", 1)
                        .locale(new Locale("pt", "BR"))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(proposta.getId()))
                .andExpect(jsonPath("$.nome").value(proposta.getNome()))
                .andExpect(jsonPath("$.propostaStatus").value("ELEGIVEL"));
    }
}

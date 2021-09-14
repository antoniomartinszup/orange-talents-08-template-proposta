package br.com.zupacademy.antonio.proposta.proposta;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropostaRepository propostaRepository;

    Gson gson = new Gson();

    @Test
    @DisplayName("Cadastra Proposta com sucesso")
    public void cadastraPropostaComSucesso() throws Exception {

        PropostaForm propostaForm = new PropostaForm("064.326.849-94", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        mockMvc.perform(post("/propostas")
                        .content(gson.toJson(propostaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Falha no cadastro da Proposta atributo documento formato invalido")
    public void falhaNoCadastroPropostaFormatoDocumento() throws Exception {

        PropostaForm propostaForm = new PropostaForm("064.326.849-945", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        mockMvc.perform(post("/propostas")
                        .locale(new Locale("pt", "BR"))
                        .content(gson.toJson(propostaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("documento"))
                .andExpect(jsonPath("$[0].erro").exists())
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Falha no cadastro da Proposta atributo documento duplicado")
    public void falhaNoCadastroPropostaFormatoDocumentoDuplicado() throws Exception {

        Proposta proposta = new Proposta("064.326.849-94", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));
        propostaRepository.save(proposta);

        PropostaForm propostaForm = new PropostaForm("064.326.849-94", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        mockMvc.perform(post("/propostas")
                        .locale(new Locale("pt", "BR"))
                        .content(gson.toJson(propostaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().is(422))
                .andExpect(jsonPath("$.campo").value("422 UNPROCESSABLE_ENTITY Proposta já enviada para o documento apresentado!"))
                .andExpect(jsonPath("$.erro").exists())
                .andReturn().getResponse();
    }
}

package br.com.zupacademy.antonio.proposta.bloqueio;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import br.com.zupacademy.antonio.proposta.proposta.PropostaForm;
import br.com.zupacademy.antonio.proposta.proposta.PropostaRepository;
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

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class BloqueioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private PropostaRepository propostaRepository;

    Gson gson = new Gson();

    @BeforeEach
    void setup() {
        cartaoRepository.deleteAll();
        propostaRepository.deleteAll();
    }

    @Test
    @DisplayName("Falha no Bloqueio Cartão não encontrado")
    public void falhaNoBloqueioCartaoNaoEncontrado() throws Exception {

        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
        cartaoRepository.save(cartao);

        mockMvc.perform(post("/bloqueios/cartoes/2222-1111-1111-2222")
                        .locale(new Locale("pt", "BR"))
                        .header("User-Agent", "PostmanRuntime/7.28.4")
                        .header("Host", "127.0.0.1")
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Falha no Bloqueio Cartão User-Agent não encontrado")
    public void falhaNoBloqueioCartaoUserAgentNaoEncontrado() throws Exception {

        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
        cartaoRepository.save(cartao);

        mockMvc.perform(post("/bloqueios/cartoes/5514-7361-7379-6190")
                        .locale(new Locale("pt", "BR"))
                        .header("Host", "127.0.0.1")
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Falha no Bloqueio Cartão Host não encontrado")
    public void falhaNoBloqueioCartaoHostNaoEncontrado() throws Exception {

        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
        cartaoRepository.save(cartao);

        mockMvc.perform(post("/bloqueios/cartoes/5514-7361-7379-6190")
                        .locale(new Locale("pt", "BR"))
                        .header("User-Agent", "PostmanRuntime/7.28.4")
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Bloqueio Cartão com sucesso")
    public void bloqueiaCartaoComSucesso() throws Exception {

        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
        cartaoRepository.save(cartao);

        mockMvc.perform(post("/bloqueios/cartoes/5514-7361-7379-6190")
                        .locale(new Locale("pt", "BR"))
                        .header("User-Agent", "PostmanRuntime/7.28.4")
                        .header("Host", "127.0.0.1")
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.bloqueioEm").exists())
                .andExpect(jsonPath("$.idBloqueado").value("5514-7361-7379-6190"));
    }
}

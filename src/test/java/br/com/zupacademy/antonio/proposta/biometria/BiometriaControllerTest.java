package br.com.zupacademy.antonio.proposta.biometria;

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
class BiometriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Autowired
    private PropostaRepository propostaRepository;

    Gson gson = new Gson();

    @BeforeEach
    void setup() {
        biometriaRepository.deleteAll();
        cartaoRepository.deleteAll();
        propostaRepository.deleteAll();
    }

    @Test
    @DisplayName("Falha no cadastro Biometria numero cartão não encontrado")
    public void falhaCadastroBiometriaNumeroCartao() throws Exception {

        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
        cartaoRepository.save(cartao);

        BiometriaForm biometriaForm = new BiometriaForm("Antonio Martins");

        mockMvc.perform(post("/biometrias/cartoes/6666-3333-3333-6666")
                        .locale(new Locale("pt","BR"))
                        .content(gson.toJson(biometriaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Falha no cadastro Biometria atributo fingerPrint esta vazio")
    public void falhaCadastroBiometriaAtributoVazio() throws Exception {

        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
        cartaoRepository.save(cartao);

        BiometriaForm biometriaForm = new BiometriaForm("");

        mockMvc.perform(post("/biometrias/cartoes/5514-7361-7379-6190")
                        .locale(new Locale("pt","BR"))
                        .content(gson.toJson(biometriaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("fingerPrint"))
                .andExpect(jsonPath("$[0].erro").value("não deve estar em branco"))
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Falha no cadastro Biometria atributo fingerPrint nulo")
    public void falhaCadastroBiometriaAtributoNulo() throws Exception {

        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
        cartaoRepository.save(cartao);

        BiometriaForm biometriaForm = new BiometriaForm(null);

        mockMvc.perform(post("/biometrias/cartoes/5514-7361-7379-6190")
                        .locale(new Locale("pt","BR"))
                        .content(gson.toJson(biometriaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("fingerPrint"))
                .andExpect(jsonPath("$[0].erro").value("não deve estar em branco"))
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Cadastro Biometria com sucesso")
    public void cadastroBiometriaComSucesso() throws Exception {

        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
                "Rua Waldemar Eggers", new BigDecimal("400.00"));

        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
        cartaoRepository.save(cartao);

        BiometriaForm biometriaForm = new BiometriaForm("Antonio Martins");

        mockMvc.perform(post("/biometrias/cartoes/5514-7361-7379-6190")
                        .locale(new Locale("pt","BR"))
                        .content(gson.toJson(biometriaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.titular").value("Antonio"));
    }
}

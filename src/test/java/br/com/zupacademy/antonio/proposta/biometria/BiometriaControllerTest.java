package br.com.zupacademy.antonio.proposta.biometria;

import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
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

    Gson gson = new Gson();

    @Test
    @DisplayName("Falha no cadastro Biometria numero cartão não encontrado")
    public void falhaCadastroBiometriaNumeroCartao() throws Exception {

        BiometriaForm biometriaForm = new BiometriaForm("Antonio Martins");

        mockMvc.perform(post("/biometrias/cartoes/id")
                        .content(gson.toJson(biometriaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Falha no cadastro Biometria")
    public void falhaCadastroBiometria() throws Exception {

        BiometriaForm biometriaForm = new BiometriaForm("");

        mockMvc.perform(post("/biometrias/cartoes/id")
                        .locale(new Locale("pt","BR"))
                        .content(gson.toJson(biometriaForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("fingerPrint"))
                .andExpect(jsonPath("$[0].erro").value("não deve estar em branco"))
                .andReturn().getResponse();
    }
}

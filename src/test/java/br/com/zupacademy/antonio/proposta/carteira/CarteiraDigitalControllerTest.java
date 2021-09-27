package br.com.zupacademy.antonio.proposta.carteira;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CarteiraDigitalControllerTest {

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
    @DisplayName("Falha na associacao cartão não encontrado")
    public void falhaNaAssociacaoCartaoNaoEncontrado() throws Exception {

        CarteiraDigitalForm carteiraDigitalForm = new CarteiraDigitalForm("antonio@email.com", ModeloCarteira.PAYPAL);

        mockMvc.perform(post("/carteiras/cartoes/id")
                        .locale(new Locale("pt", "BR"))
                        .content(gson.toJson(carteiraDigitalForm))
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());
    }

    //continuar

//    @Test
//    @DisplayName("Asociacao cartão com sucesso")
//    public void associacaoCartaoComSucesso() throws Exception {
//
//        PropostaForm propostaForm = new PropostaForm("642.325.460-57", "antonio@email.com", "Antonio",
//                "Rua Waldemar Eggers", new BigDecimal("400.00"));
//
//        Cartao cartao = new Cartao("5514-7361-7379-6190", "Antonio", LocalDateTime.now(),
//                new BigDecimal("1.00"), propostaForm.converteParaModelProposta(propostaRepository));
//        cartaoRepository.save(cartao);
//
//        CarteiraDigitalForm carteiraDigitalForm = new CarteiraDigitalForm("antonio@email.com", ModeloCarteira.PAYPAL);
//
//        mockMvc.perform(post("/carteiras/cartoes/5514-7361-7379-6190")
//                        .locale(new Locale("pt", "BR"))
//                        .content(gson.toJson(carteiraDigitalForm))
//                        .contentType(MediaType.APPLICATION_JSON))
//
//                .andDo(print())
//                .andExpect(status().isCreated());
//    }
}
